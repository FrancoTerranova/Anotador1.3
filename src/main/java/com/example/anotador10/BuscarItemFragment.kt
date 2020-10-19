package com.example.anotador10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.DataBase.DbViewModel
import com.example.anotador10.databinding.FragmentVerListasBinding
import com.example.classes.RangoDialogFragment
import com.example.classes.RangoValorDialogFragment
import com.example.classes.ValorUnicoDialogFragment
import com.example.viewModels.BuscarItemViewModel
import com.example.viewModels.ControlViewModel

import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.time.YearMonth


class BuscarItemFragment : Fragment() {

  //  var rangodialogo : MaterialAlertDialogBuilder? = null
    lateinit var buscaritembind : FragmentVerListasBinding
    lateinit var controlVM : ControlViewModel
    lateinit var buscarItemViewModel : BuscarItemViewModel
    lateinit var  dbVM : DbViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Bindings(inflater, container)
        val view : View = buscaritembind.root
        buscaritembind.control = controlVM


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Observar(view)

        
        // falta para los otros
        if(buscarItemViewModel.periodo_opcion_selec.equals("diaDeLaFecha")){
            val modPeriodo : Button = view.findViewById(R.id.ModificarPer)
                modPeriodo.isEnabled = false
            modPeriodo.isClickable = false
                }




        }

fun Observar(viewGlobal: View){


       buscarItemViewModel.busquedaconfigchange.observe(viewLifecycleOwner, Observer { ch ->
           if (ch) {
               buscarItemViewModel.busquedaConfig.observe(viewLifecycleOwner, Observer { rn ->
                   //buscarItemViewModel.setConfigChange(false)
                   val rangtxt2 = viewGlobal.findViewById<TextView>(R.id.txtRango)
                   val valtxt = viewGlobal.findViewById<TextView>(R.id.txtValor)
                   rangtxt2.text = ("Periodo: " + rn.rngtxt)
                   if(rn.valoRtxt.equals("cualquier")){
                       valtxt.text = ("Valor establecido: Cualquier valor")
                   }
                   else
                   valtxt.text = ("Valor establecido: $" + rn.valoRtxt)
                   //Navigation.findNavController(viewGlobal).navigate(R.id.action_verListasFragment_self)
               })

           }
       }
       )





    dbVM.AniosListinit.observe(viewLifecycleOwner, Observer { Ainit ->
        if(Ainit){
            dbVM.anios_list.observe(viewLifecycleOwner, Observer { an ->
                buscarItemViewModel.anios = an
            })

        }
    })

    controlVM.error.observe(viewLifecycleOwner, Observer { err ->
        if(err){

            controlVM.causaError.observe(viewLifecycleOwner, Observer { cau ->
                controlVM.origenError.observe(viewLifecycleOwner, Observer { or ->
                    if(cau.equals("Invalido") && or.equals("valorXRango")){
                        Toast.makeText(requireActivity(),"Debe ingresar algun valor!", Toast.LENGTH_LONG).show()
                        val diag = RangoValorDialogFragment()
                        diag.show( (context as MainActivity).supportFragmentManager,"buscarValxrango")
                        controlVM.limpiarError()
                    }

                })

            })

        }

    })


    controlVM.accion.observe(viewLifecycleOwner, Observer { acc ->
        val porR: AppCompatCheckBox = viewGlobal.findViewById(R.id.porrango)
        val Lmonth: AppCompatCheckBox = viewGlobal.findViewById(R.id.lastMonth)
        val L7dias: AppCompatCheckBox = viewGlobal.findViewById(R.id.last7dias)
        val DFecha: AppCompatCheckBox = viewGlobal.findViewById(R.id.diaDeLaFecha)
        val valPorRang : AppCompatCheckBox = viewGlobal.findViewById(R.id.valXrango)
        val valUnico : AppCompatCheckBox = viewGlobal.findViewById(R.id.valUnico)
        val valCualquier : AppCompatCheckBox = viewGlobal.findViewById(R.id.valCualquier)
        val modPeriodo : Button = viewGlobal.findViewById(R.id.ModificarPer)
        val modVal : Button = viewGlobal.findViewById(R.id.ModValor)
       // val limpiarPer : Button = viewGlobal.findViewById(R.id.limpiarPer)

        if(acc.equals("Buscar")){
            controlVM.setAccion("")

            if(  !porR.isChecked && !Lmonth.isChecked && !L7dias.isChecked && !DFecha.isChecked){
                Toast.makeText(requireActivity(),"Debe seleccionar una busqueda para el periodo!",Toast.LENGTH_LONG).show()

            }
            else
            if((valUnico.isChecked || valCualquier.isChecked  || valPorRang.isChecked )&&
                    !porR.isChecked && !Lmonth.isChecked && !L7dias.isChecked && !DFecha.isChecked
            )
                Toast.makeText(requireActivity(),"Debe seleccionar una busqueda para el periodo!",Toast.LENGTH_LONG).show()

            else
            if(!valUnico.isChecked && !valCualquier.isChecked && !valPorRang.isChecked){
                Toast.makeText(requireActivity(),"Debe seleccionar una busqueda para el valor!",Toast.LENGTH_LONG).show()
            }


            else {
                buscarItemViewModel.busquedaConfig.observe(viewLifecycleOwner, Observer { busqueda ->
                    dbVM.buscarItems(busqueda)
                    dbVM.items_encontrados_ready.observe(viewLifecycleOwner, Observer { r ->
                        if (r) {
                            dbVM.setItemReady(false)
                            Navigation.findNavController(viewGlobal).navigate(R.id.action_verListasFragment_to_resultadosItemsFragment)
                        }

                    })
                })
            }
        }
        if(acc.equals("limpiarPer")){
            controlVM.setAccion("")
            buscarItemViewModel.limpiarPeriodo()
        }
        if(acc.equals("ModValor")){
            controlVM.setAccion("")
           if(valUnico.isChecked){
               val di = ValorUnicoDialogFragment()
               di.show( (context as MainActivity).supportFragmentManager,"unicValor")
           }
            else
               if(valPorRang.isChecked){
                   val di = RangoValorDialogFragment()
                   di.show( (context as MainActivity).supportFragmentManager,"rangValor")
               }
        }
        if (acc.equals("porRango")) {
            controlVM.setAccion("")

            if (porR.isChecked) {
                buscarItemViewModel.periodo_opcion_selec = "porRango"
                Lmonth.isChecked = false
                L7dias.isChecked = false
                DFecha.isChecked = false
                valPorRang.isChecked = false
                valUnico.isChecked = false
                valCualquier.isChecked = false
                modPeriodo.isEnabled = true
                modPeriodo.isClickable = true
                buscarItemViewModel.nuevaBusqueda()
                val di = RangoDialogFragment()
                di.show( (context as MainActivity).supportFragmentManager,"buscarxrango")
            }
        }
        else
            // desde 1 hasta fin de mes anterior
            if (acc.equals("lastMonth")) {
                controlVM.setAccion("")

                if (Lmonth.isChecked) {
                    buscarItemViewModel.periodo_opcion_selec = "lastMonth"
                    porR.isChecked = false
                    L7dias.isChecked = false
                    DFecha.isChecked = false

                    valPorRang.isChecked = false
                    valUnico.isChecked = false
                    valCualquier.isChecked = false

                    modPeriodo.isEnabled = false
                    modPeriodo.isClickable = false
                    buscarItemViewModel.nuevaBusqueda()

                    buscarItemViewModel.setLastMonth()




                }
            }
            else
                if (acc.equals("diaDeLaFecha")) {
                    controlVM.setAccion("")

                    if (DFecha.isChecked) {
                        buscarItemViewModel.periodo_opcion_selec = "diaDeLaFecha"
                        Lmonth.isChecked = false
                        L7dias.isChecked = false
                        porR.isChecked = false

                        valPorRang.isChecked = false
                        valUnico.isChecked = false
                        valCualquier.isChecked = false
                        modPeriodo.isEnabled = false
                        modPeriodo.isClickable = false
                        buscarItemViewModel.nuevaBusqueda()

                        buscarItemViewModel.setDiaDeLaFecha()

                    }
                }
                else
                    if (acc.equals("last7dias")) {
                        controlVM.setAccion("")

                        if (L7dias.isChecked) {
                            buscarItemViewModel.periodo_opcion_selec = "last7dias"
                            Lmonth.isChecked = false
                            porR.isChecked = false
                            DFecha.isChecked = false

                            valPorRang.isChecked = false
                            valUnico.isChecked = false
                            valCualquier.isChecked = false
                            modPeriodo.isEnabled = false
                            modPeriodo.isClickable = false

                            buscarItemViewModel.nuevaBusqueda()

                            buscarItemViewModel.setLast7Dias()
                        }
                    }
        else
                        if(acc.equals("valorPorRango")){
                            controlVM.setAccion("")
                            if(valPorRang.isChecked) {
                                valCualquier.isChecked = false
                                valUnico.isChecked = false
                                modVal.isEnabled = true
                                modVal.isClickable = true
                                val diag = RangoValorDialogFragment()
                                diag.show( (context as MainActivity).supportFragmentManager,"buscarValxrango")
                            }
                        }
        else
                            if(acc.equals("valorUnico")){
                                controlVM.setAccion("")
                                if(valUnico.isChecked) {
                                    valPorRang.isChecked = false
                                    valCualquier.isChecked = false

                                    modVal.isEnabled = true
                                    modVal.isClickable = true
                                    val diag = ValorUnicoDialogFragment()
                                    diag.show( (context as MainActivity).supportFragmentManager,"buscarValunico")
                                }
                            }
        else
                                if(acc.equals("valorCualquier")) {
                                    controlVM.setAccion("")
                                if(valCualquier.isChecked){

                                    modVal.isEnabled = false
                                    modVal.isClickable = false

                                    valUnico.isChecked = false
                                    valPorRang.isChecked = false
                                    buscarItemViewModel.setCualquierValor()
                                }

                                }

        else
                                if(acc.equals("ModificarPer")){
                                    controlVM.setAccion("")
                                    val opcion = buscarItemViewModel.periodo_opcion_selec
                                    if(opcion != "" && opcion.equals("porRango")){
                                        val di = RangoDialogFragment()
                                        di.show( (context as MainActivity).supportFragmentManager,"buscarxrango")
                                    }
                                }



    })
}


    fun Bindings(inflater: LayoutInflater, container: ViewGroup?){
        buscaritembind = DataBindingUtil.inflate(inflater, R.layout.fragment_ver_listas, container, false)
        controlVM = ViewModelProvider(requireActivity()).get(ControlViewModel::class.java)
        buscarItemViewModel = ViewModelProvider(requireActivity()).get(BuscarItemViewModel::class.java)
        dbVM = ViewModelProvider(requireActivity()).get(DbViewModel::class.java)
    }




}