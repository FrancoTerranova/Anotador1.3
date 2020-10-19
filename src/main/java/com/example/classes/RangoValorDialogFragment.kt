package com.example.classes

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.anotador10.R
import com.example.viewModels.BuscarItemViewModel
import com.example.viewModels.ControlViewModel
import java.lang.StringBuilder

class RangoValorDialogFragment : DialogFragment() {
    var v : View? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val buscarItemViewModel = ViewModelProvider(requireActivity()).get(BuscarItemViewModel::class.java)
            v = layoutInflater.inflate(R.layout.rango_valor_dialog, null)

            val valDesde = v?.findViewById<EditText>(R.id.valorDesde)
            val valHasta = v?.findViewById<EditText>(R.id.valorHasta)

            valDesde?.setText(buscarItemViewModel.valor_desde)
            valHasta?.setText(buscarItemViewModel.valor_hasta)




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
        val valDesde = v?.findViewById<EditText>(R.id.valorDesde)
        val valHasta = v?.findViewById<EditText>(R.id.valorHasta)


        val stbuild = StringBuilder()

        val valdsd = valDesde?.text.toString()
        val valhst = valHasta?.text.toString()

        if((valdsd == "") || (valhst == "")){
         //   Toast.makeText(requireActivity(),"Debe ingresar algun valor",Toast.LENGTH_LONG).show()
            val controlViewModel = ViewModelProvider(requireActivity()).get(ControlViewModel::class.java)
            controlViewModel.setError("Invalido","valorXRango",true)
        }
        else {

            stbuild.append(valDesde?.text.toString())
            stbuild.append("-")
            stbuild.append(valHasta?.text.toString())

            buscarItemViewModel.valor_desde = valDesde?.text.toString()
            buscarItemViewModel.valor_hasta = valHasta?.text.toString()

            buscarItemViewModel.configurarBusqueda("", stbuild.toString())
        }
    }


}