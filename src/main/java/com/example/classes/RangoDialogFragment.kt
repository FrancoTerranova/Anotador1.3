package com.example.classes

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.anotador10.R
import com.example.viewModels.BuscarItemViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.anios_hasta_list.view.*
import kotlinx.android.synthetic.main.anios_list.view.*
import kotlinx.android.synthetic.main.anios_list.view.textianios
import kotlinx.android.synthetic.main.dias_hasta_list.view.*
import kotlinx.android.synthetic.main.dias_list.view.*
import kotlinx.android.synthetic.main.mes_hasta_list.view.*
import kotlinx.android.synthetic.main.meses_list.view.*
import kotlinx.android.synthetic.main.rango_dialog.*
import kotlinx.android.synthetic.main.rango_dialog.view.*
import java.lang.StringBuilder

class RangoDialogFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    var a = ""
    var spinDias : Spinner? = null
    var spinDiasHasta : Spinner? = null
    var v : View? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{

            val buscarItemViewModel = ViewModelProvider(requireActivity()).get(BuscarItemViewModel::class.java)
            v = layoutInflater.inflate(R.layout.rango_dialog, null)

            val anioslis = buscarItemViewModel.getAnios()
            val meseslis = buscarItemViewModel.getMeses()


            spinDias = v?.findViewById<Spinner>(R.id.diasDesde)
            spinDiasHasta = v?.findViewById(R.id.diasHasta)
            val anios = v?.findViewById<Spinner>(R.id.aniosDesde)

            val adapter1 = ArrayAdapter(requireActivity(), R.layout.anios_list,anioslis )

            val mesesly = v?.findViewById<Spinner>(R.id.mesDesde)

            val adapter2 = ArrayAdapter(requireActivity(), R.layout.meses_list,meseslis )


            val anioshasta = v?.findViewById<Spinner>(R.id.elquefunciona)
            val adapterAnioshasta = ArrayAdapter(requireActivity(), R.layout.anios_hasta_list,anioslis )

            val mesesHasta = v?.findViewById<Spinner>(R.id.mesHasta)

            val adapterMeseshasta = ArrayAdapter(requireActivity(), R.layout.mes_hasta_list,meseslis )

            mesesly?.adapter = adapter2
            mesesly?.onItemSelectedListener = this

            if(buscarItemViewModel.mes_selec != "") {
                val ms = buscarItemViewModel.mes_selec
                mesesly?.setSelection(buscarItemViewModel.Mestonumber(ms) - 1)
            }

            anios?.adapter = adapter1
            anios?.onItemSelectedListener = this

            mesesHasta?.adapter = adapterMeseshasta
            mesesHasta?.onItemSelectedListener = this
            if(buscarItemViewModel.mes_selec_hasta != "") {
                val ms = buscarItemViewModel.mes_selec_hasta
                mesesHasta?.setSelection(buscarItemViewModel.Mestonumber(ms) - 1)
            }

            anioshasta?.adapter = adapterAnioshasta
            anioshasta?.onItemSelectedListener = this



           buscarItemViewModel.mes_selec = "Marzo"
            buscarItemViewModel.anio_selec = "2020"

            buscarItemViewModel.mes_selec_hasta = "Junio"
            buscarItemViewModel.anio_selec_hasta = "2020"

            var  d = buscarItemViewModel.getDias("D")
            val adapter = ArrayAdapter(requireActivity(), R.layout.dias_list,d )

            spinDias?.adapter = adapter
            spinDias?.onItemSelectedListener = this
            if(buscarItemViewModel.dia_selec != "") {
                val ms = buscarItemViewModel.dia_selec
                spinDias?.setSelection(ms.toInt() - 1)
            }

            var  dH = buscarItemViewModel.getDias("H")
            val adapterdiashasta = ArrayAdapter(requireActivity(), R.layout.dias_hasta_list,dH )
            spinDiasHasta?.adapter = adapterdiashasta
            spinDiasHasta?.onItemSelectedListener = this
            if(buscarItemViewModel.dia_selec_hasta != "") {
                val ms = buscarItemViewModel.dia_selec_hasta
                spinDiasHasta?.setSelection(ms.toInt() - 1)
            }

            val builder = AlertDialog.Builder(it)

            builder.setMessage("Prueba")
                    .setView(v)
                    .setPositiveButton("Aceptar",
                            DialogInterface.OnClickListener { dialog, id ->
                                crearRango()

                            })
                    .setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun crearRango(){
        val buscarItemViewModel = ViewModelProvider(requireActivity()).get(BuscarItemViewModel::class.java)
        val rang : String = ""
        val valorRang : String = ""

        val anDsd = buscarItemViewModel.anio_selec
        val mesDsd = buscarItemViewModel.mes_selec
        val diaDsd = buscarItemViewModel.dia_selec

        val anHst = buscarItemViewModel.anio_selec_hasta
        val meshst = buscarItemViewModel.mes_selec_hasta
        val diahst = buscarItemViewModel.dia_selec_hasta

        val rangBuild = StringBuilder()
        val rangVBuild = StringBuilder()

        rangBuild.append(diaDsd)
        rangBuild.append("/")
        rangBuild.append((buscarItemViewModel.Mestonumber(mesDsd)).toString())
        rangBuild.append("/")
        rangBuild.append(anDsd)

        rangBuild.append(",")

        rangBuild.append(diahst)
        rangBuild.append("/")
        rangBuild.append((buscarItemViewModel.Mestonumber(meshst)).toString())
        rangBuild.append("/")
        rangBuild.append(anHst)

        buscarItemViewModel.configurarBusqueda(rangBuild.toString(),"")

    }


    fun cambiar( mesoanio : String, id : Int){
        val buscarItemViewModel = ViewModelProvider(requireActivity()).get(BuscarItemViewModel::class.java)
        if(id == R.id.textimes){

            buscarItemViewModel.mes_selec = mesoanio
            var  d = buscarItemViewModel.getDias("D")
            val adapteR = ArrayAdapter(requireActivity(), R.layout.dias_list,d )
            spinDias?.adapter = adapteR
            if(buscarItemViewModel.dia_selec != "") {
                val ms = buscarItemViewModel.dia_selec
                spinDias?.setSelection(ms.toInt() - 1)
            }
        }

        else
            if(id == R.id.textianios) {

                buscarItemViewModel.anio_selec = mesoanio
                var dH = buscarItemViewModel.getDias("D")
                val adapteR = ArrayAdapter(requireActivity(), R.layout.dias_list, dH)
                spinDias?.adapter = adapteR
                if(buscarItemViewModel.dia_selec != "") {
                    val ms = buscarItemViewModel.dia_selec
                    spinDias?.setSelection(ms.toInt() - 1)
                }
            }
        else
                if(id == R.id.textianiosHasta){
                    buscarItemViewModel.anio_selec_hasta = mesoanio
                    var  d = buscarItemViewModel.getDias("H")
                    val adapteR = ArrayAdapter(requireActivity(), R.layout.dias_hasta_list,d )
                    spinDiasHasta?.adapter = adapteR
                    if(buscarItemViewModel.dia_selec_hasta != "") {
                        val ms = buscarItemViewModel.dia_selec_hasta
                        spinDiasHasta?.setSelection(ms.toInt() - 1)
                    }
                }
                else
                    if(id == R.id.textimesHasta){
                        buscarItemViewModel.mes_selec_hasta = mesoanio
                        var  d = buscarItemViewModel.getDias("H")
                        val adapteR = ArrayAdapter(requireActivity(), R.layout.dias_hasta_list,d )
                        spinDiasHasta?.adapter = adapteR


                        if(buscarItemViewModel.dia_selec_hasta != "") {
                            val ms = buscarItemViewModel.dia_selec_hasta
                            spinDiasHasta?.setSelection(ms.toInt() - 1)
                        }
                    }
        else
                        if(id == R.id.texti){
                            buscarItemViewModel.dia_selec = mesoanio
                        }
        else
                            if(id == R.id.textidiasHasta){
                                buscarItemViewModel.dia_selec_hasta = mesoanio
                            }





    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      when(p1?.id){
           R.id.textimes ->  {

               cambiar( p1.textimes.text.toString(),R.id.textimes)
           }
          R.id.textianios -> {
              cambiar( p1.textianios.text.toString(),R.id.textianios)
          }
          R.id.textianiosHasta ->{
              cambiar( p1.textianiosHasta.text.toString(),R.id.textianiosHasta)
          }
          R.id.textimesHasta ->{
              cambiar( p1.textimesHasta.text.toString(),R.id.textimesHasta)
          }
          R.id.texti -> {
              cambiar( p1.texti.text.toString(),R.id.texti)
          }
          R.id.textidiasHasta -> {
              cambiar( p1.textidiasHasta.text.toString(),R.id.textidiasHasta)
          }

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}