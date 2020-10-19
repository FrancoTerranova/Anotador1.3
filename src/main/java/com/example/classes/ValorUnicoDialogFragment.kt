package com.example.classes

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.anotador10.R
import com.example.viewModels.BuscarItemViewModel

class ValorUnicoDialogFragment : DialogFragment() {

    var v : View? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {


            v = layoutInflater.inflate(R.layout.valor_unico_dialog, null)



            val builder = AlertDialog.Builder(it)

            builder.setMessage("Prueba")
                    .setView(v)
                    .setPositiveButton("Aceptar",
                            DialogInterface.OnClickListener { dialog, id ->
                                crearValor()
                            })
                    .setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun crearValor(){
        val buscarItemViewModel = ViewModelProvider(requireActivity()).get(BuscarItemViewModel::class.java)
        val valoR = v?.findViewById<EditText>(R.id.valorUnico)

        buscarItemViewModel.configurarBusqueda("",valoR?.text.toString())
    }


}