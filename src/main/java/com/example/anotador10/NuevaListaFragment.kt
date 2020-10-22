package com.example.anotador10

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.DataBase.DbViewModel
import com.example.anotador10.databinding.FragmentNuevaListaBinding
import com.example.classes.CustomEditText
import com.example.classes.ItemsAgregadosDialogFragment
import com.example.viewModels.ControlViewModel
import com.example.viewModels.ListaViewModel
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


class NuevaListaFragment : Fragment(), TextWatcher {

    lateinit var controlVM : ControlViewModel
    lateinit var nuevalistabinding : FragmentNuevaListaBinding
    lateinit var dbVM : DbViewModel
    lateinit var listVM : ListaViewModel
    var es_editar = false
    var guardar_lista = false
    var valorcito : String = ""
    var cont : Int = 0
    var guardar_editado = false
    lateinit var buttonagregar : Button
    var  toast_error_item : Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        Bindings(inflater, container)
        val view : View = nuevalistabinding.root
        nuevalistabinding.control = controlVM
        return view
    }
    fun Bindings(inflater: LayoutInflater, container: ViewGroup?){
        nuevalistabinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nueva_lista, container, false)
        controlVM =  ViewModelProvider(requireActivity()).get(ControlViewModel::class.java)
        dbVM = ViewModelProvider(requireActivity()).get(DbViewModel::class.java)
        listVM = ViewModelProvider(requireActivity()).get(ListaViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Observar(view)


        listVM.iniciar()

        val t : TextView = view.findViewById(R.id.titulo)
        dbVM.listas.observe(viewLifecycleOwner, Observer { lis ->
            t.text = lis.size.toString()
        })

        val tx : CustomEditText = view.findViewById(R.id.custET)
        tx.addTextChangedListener(this)

       buttonagregar  = view.findViewById<Button>(R.id.addItem)
        buttonagregar.isEnabled = false
        buttonagregar.isClickable = false

        actualizarTotal(view)



    }
    fun Observar(viewGlobal: View){

        listVM.editar_item.observe(viewLifecycleOwner, Observer { edit ->
            if (edit) {
                listVM.itemAEditar(false)


                val nom: EditText = viewGlobal.findViewById(R.id.ItemNomb)

                es_editar = true
                guardar_editado = true


                var precioDou  =  BigDecimal.valueOf(listVM.item_a_editar?.Precio!!)
                precioDou =  precioDou.setScale(2, RoundingMode.HALF_UP)
                if (precioDou.toString().contains(".")) {
                    val antescoma = String.format(Locale.ITALIAN, "%,d", precioDou?.toLong())
                    val dspcoma = precioDou.toString().substringAfter(".")
                    listVM.seTprueb("$" + antescoma + "," + dspcoma)
                    nom.setText(listVM.item_a_editar?.NombreItem?.trim())
                } else {
                    val enterito = String.format(Locale.ITALIAN, "%,d", precioDou?.toLong())
                    listVM.seTprueb("$" + enterito)
                    nom.setText(listVM.item_a_editar?.NombreItem?.trim())
                }
                es_editar = false


            }
        })

                listVM.prueb.observe(viewLifecycleOwner, Observer { txs ->
                    val tx: CustomEditText = viewGlobal.findViewById(R.id.custET)
                    if (txs != tx.text.toString()) {


                        tx.setText(txs)

                    }
                })
                controlVM.accion.observe(viewLifecycleOwner, Observer { id ->

                    if (id.equals(getString(R.string.addItem))) {
                        controlVM.setAccion("")
                        val edtxt: CustomEditText = viewGlobal.findViewById(R.id.custET)
                        val nomTxt: EditText = viewGlobal.findViewById(R.id.ItemNomb)
                        if (edtxt.text.toString().isNotEmpty() && nomTxt.text.toString().isNotEmpty()) {

                            var c = edtxt.text.toString().replace(".", "").replace(",", ".").replace("$", "")

                            // para recuperar de la BD
                            var te = c.substringBefore(".")
                            var fe = String.format(Locale.ITALIAN, "%,d", te.toLong())
                            fe = fe + "," + c.substringAfter(".")
                            //
                            if (!guardar_editado) {
                                listVM.addItem(nomTxt.text.toString().trim(), c.toDouble())
                                Toast.makeText(requireActivity(), "Item agregado con exito!", Toast.LENGTH_SHORT).show()
                                actualizarTotal(viewGlobal)
                                LimpiarInterfaz(edtxt, nomTxt)
                            } else {
                                listVM.editarItem(nomTxt.text.toString(), c.toDouble())
                                Toast.makeText(requireActivity(), "Item modificado con exito!", Toast.LENGTH_SHORT).show()
                                actualizarTotal(viewGlobal)
                                LimpiarInterfaz(edtxt, nomTxt)
                                guardar_editado = false
                            }
                        } else {

                        if(!nomTxt.text.toString().isNotEmpty()) {
                            if (toast_error_item != null) {
                                toast_error_item!!.cancel()
                            }
                            toast_error_item = Toast.makeText(requireActivity(), "Debe indicar un nombre!", Toast.LENGTH_SHORT)
                            toast_error_item!!.show()
                        }

                        }

                    } else
                        if (id.equals(getString(R.string.editItem))) {
                            val di = ItemsAgregadosDialogFragment()
                            di.show((context as MainActivity).supportFragmentManager, "listAgregados")
                        } else
                            if (id.equals(getString(R.string.guardarItems))) {

                                controlVM.setAccion("")
                                val edtxt: EditText = EditText(requireActivity())
                                val edtxt1: EditText = EditText(requireActivity())
                                val linlay2: LinearLayout = LinearLayout(requireActivity())


                                edtxt.hint = "Nombre de la lista..."
                                edtxt1.hint = "Descripcion(opcional)..."

                                linlay2.orientation = TextInputLayout.VERTICAL
                                linlay2.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                                linlay2.setPadding(50, 50, 50, 50)
                                linlay2.addView(edtxt)
                                linlay2.addView(edtxt1)
                                // TODO

                                val alert = AlertDialog.Builder(requireActivity())
                                alert.setMessage("Guardar Lista")

                                        .setView(linlay2)
                                        .setPositiveButton("Aceptar") { _, _ ->
                                            guardar_lista = true
                                            guardarLista(edtxt.text.toString(), edtxt1.text.toString())
                                            actualizarTotal(viewGlobal)

                                        }
                                        .setNegativeButton("Cancelar") { _, _ ->
                                        }
                                        .show()


                            }


                })



    }

    fun medirDigitosEnteros(valr : String) : Boolean{
        var auxi = valr
        if(auxi.contains("$"))
            auxi = auxi.replace("$","")
        val largito = auxi.length
        if(largito <= 11)
            return true

        return false


    }


    fun LimpiarInterfaz(valor: CustomEditText, nombre: EditText){
            valor.setText("")
            nombre.setText("")

    }
    fun guardarLista(nomlis: String, descr: String?){
        listVM.list.observe(viewLifecycleOwner, Observer { l ->
            listVM.items.observe(viewLifecycleOwner, Observer { its ->
                if (its.size > 0 && (guardar_lista == true)) {
                    listVM.guardarDatos(nomlis, descr)
                    dbVM.guardarLista(l, its)

                    listVM.limpiarDatos()
                    guardar_lista = false
                }


            })


        })
    }
    fun actualizarTotal(viewGlobal: View){
            val tot : TextView = viewGlobal.findViewById(R.id.totalitems)

             var totatilo = listVM.getTotal()




        var auxi =  BigDecimal.valueOf(totatilo)
        auxi =  auxi.setScale(2, RoundingMode.HALF_UP)

        var enterillo = String.format(Locale.ITALIAN, "%,d",totatilo.toLong() )

        var finalnum : String
        if(totatilo.toString().contains(".")) {
            finalnum = enterillo + "," + auxi.toString().substringAfter(".")
            tot.text = "$" + finalnum
        }
        else {

            tot.text = "$" + enterillo
        }
      /*  listVM.items.observe(viewLifecycleOwner, Observer { its ->
        if(its != null) {
           // val linlay: LinearLayout = viewGlobal.findViewById(R.id.itemslist)
            linlay.removeAllViews()
            val total : TextView = viewGlobal.findViewById(R.id.totalitems)
            for (it in its) {


                    val itemview: LinearLayout = LinearLayout(requireActivity())

                    itemview.orientation = LinearLayout.HORIZONTAL
                    itemview.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)


                    val et: EditText = EditText(requireActivity())
                    val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 167F, resources.displayMetrics);
                    et.layoutParams = ViewGroup.LayoutParams(width.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
                    et.id = listVM.linlyout.toInt()
                    listVM.linlyout++

                    et.setText(it.NombreItem)
                    et.isEnabled = false
                    et.isFocusable = false
                    itemview.addView(et)
                    val et1: EditText = EditText(requireActivity())
                    val width1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 167F, resources.displayMetrics);
                    et1.layoutParams = ViewGroup.LayoutParams(width.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
                    et1.setText(it.Precio.toString())
                    et1.isEnabled = false
                    et1.isFocusable = false
                    et1.inputType = InputType.TYPE_CLASS_NUMBER

                    et1.id = listVM.linlyout.toInt()


                    itemview.addView(et1)

                    linlay.addView(itemview)
                }
                total.text = listVM.totalitems.toString()

            }
        })*/

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        if(!es_editar) {
            if (p0 != null) {
                if (p0.isNotEmpty() && !p0.toString().equals("") && !p0.toString().equals("$") && !p0.toString().equals(".")) {

                    var st = p0.toString()
                    buttonagregar.isEnabled = true
                    buttonagregar.isClickable = true
                    if (!st.contains(",")) {
                        if(medirDigitosEnteros(st)) {
                            st = st.replace(".", "").replace("$", "")
                            st = String.format(Locale.ITALIAN, "%,d", st.toLong())
                            listVM.valor_anterior_entero = st
                            listVM.seTprueb("$" + st)

                        }
                        else{
                                listVM.seTprueb("$" + listVM.valor_anterior_entero)
                        }

                    } else {


                        if (st.substringAfter(",").contains("."))
                            st = st.substringBefore(",") + "," + st.substringAfter(",").replace(".", "")
                        if (st.substringAfter(",").contains(","))
                            st = st.substringBefore(",") + "," + st.substringAfter(",").replace(",", "")

                        if (st.contains(",") && p0.toString().length == 1) {
                            st = "0" + st
                            listVM.seTprueb("$" + st)
                        } else {
                            if (st.substringAfter(",").length <= 2) {
                                listVM.decimales_valor = st.substringAfter(",")
                                listVM.seTprueb(st)
                            } else {
                                st = st.substringBefore(",") + "," + listVM.decimales_valor
                                listVM.seTprueb(st)
                            }
                        }

                    }
                } else {
                    if (p0.toString().equals("$") || p0.toString().equals(".")) {
                        listVM.seTprueb("")
                        buttonagregar.isEnabled = false
                        buttonagregar.isClickable = false
                    }
                }


            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }


}




