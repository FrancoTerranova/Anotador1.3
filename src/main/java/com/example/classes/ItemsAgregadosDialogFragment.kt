package com.example.classes

import Entities.ItemLista
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.anotador10.R
import com.example.viewModels.BuscarItemViewModel
import com.example.viewModels.ListaViewModel
import com.google.android.material.textfield.TextInputLayout

class ItemsAgregadosDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            val listVM = ViewModelProvider(requireActivity()).get(ListaViewModel::class.java)
           /* val viewG = layoutInflater.inflate(R.layout.items_lista_agregados_dialog, null)
            Iniciar(listVM,viewG)*/
            val itms = listVM.getAgregados()
            val itmsNP = ArrayList<String>()
            var item_checked = false
            for(its in itms!!){

                itmsNP.add(its.NombreItem + " " + its.Precio)
            }
            val indx = arrayOf(Int,String)
            var ind = 0
            for(itS in itms){
               indx[ind] = (itS.NombreItem + "-" + itS.Precio)
                ind++
            }




            // Set the dialog title
            builder.setTitle("Agregados")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setSingleChoiceItems(itmsNP.toArray(arrayOf()),-1) { dialog, which ->
                        val itmen = indx.get(which.toInt()).toString()
                        item_checked = true
                    listVM.item_a_editar = ItemLista(
                            0,
                            0,
                            itmen.substringBefore("-"),
                            itmen.substringAfter("-").toDouble()
                            )


                    }
                    // Set the action buttons
                    .setPositiveButton("Editar",
                            DialogInterface.OnClickListener { dialog, id ->
                                if(listVM.item_a_editar != null && item_checked == true)
                                listVM.itemAEditar(true)
                                else{
                                    if(listVM.item_a_editar != null && item_checked == false)
                                    listVM.item_a_editar = null
                                }
                            })
                    .setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, id ->
                                if(listVM.item_a_editar != null)
                                    listVM.item_a_editar = null
                            })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

 /*   fun Iniciar(listVM : ListaViewModel, viewG : View){
            val itms = listVM.getAgregados()
             val lyV = viewG.findViewById<LinearLayout>(R.id.lyMaster)
        for(its in itms!!){
            val ly = LinearLayout(requireActivity())

            ly.orientation = TextInputLayout.HORIZONTAL
            ly.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)

            val edtxt = TextView(requireActivity())
            val chkbox = CheckBox(requireActivity())

            edtxt.setText(its.NombreItem + its.Precio)

            ly.addView(edtxt)
            ly.addView(chkbox)
            lyV.addView(ly)

        }

    }*/
}


