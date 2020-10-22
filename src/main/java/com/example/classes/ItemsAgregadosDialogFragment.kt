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
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList

class ItemsAgregadosDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val listVM = ViewModelProvider(requireActivity()).get(ListaViewModel::class.java)

            val itms = listVM.getAgregados()
            val itmsNP = ArrayList<String>()
            var item_checked = false
            for(its in itms!!){

                var auxi =  BigDecimal.valueOf(its.Precio)
                auxi =  auxi.setScale(2, RoundingMode.HALF_UP)
                var enterillo = String.format(Locale.ITALIAN, "%,d",its.Precio.toLong() )

                var finalnum : String
                if(its.Precio.toString().contains(".")) {
                    finalnum = enterillo + "," + auxi.toString().substringAfter(".")
                    itmsNP.add(its.NombreItem + "-" + finalnum)
                }
                else {

                    itmsNP.add(its.NombreItem + "-" + enterillo)
                }


            }
          /*  val indx = arrayOfNulls<String>(listVM.getAgregadosSize())
            var ind = 0
            for(itS in itms){
               indx[ind] = (itS.NombreItem + "-" + itS.Precio)
                ind++
            }*/




            // Set the dialog title
            builder.setTitle("Agregados")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setSingleChoiceItems(itmsNP.toArray(arrayOf()),-1) { _, which ->
                        var itmen = itmsNP.get(which.toInt())
                                //indx.get(which.toInt()).toString()
                        item_checked = true
                        if(itmen.substringAfter("-").contains(".")){
                            itmen = itmen.replace(".","")
                            if(itmen.substringAfter("-").contains(","))
                                itmen = itmen.replace(",",".")
                        }
                        else{
                            if(itmen.substringAfter("-").contains(","))
                                itmen = itmen.replace(",",".")
                        }
                    listVM.item_a_editar = ItemLista(
                            0,
                            0,
                            itmen.substringBefore("-"),
                            itmen.substringAfter("-").toDouble()
                            )


                    }
                    // Set the action buttons
                    .setPositiveButton("Editar",
                            DialogInterface.OnClickListener { _, _ ->
                                if(listVM.item_a_editar != null && item_checked == true)
                                listVM.itemAEditar(true)
                                else{
                                    if(listVM.item_a_editar != null && item_checked == false)
                                    listVM.item_a_editar = null
                                }
                            })
                    .setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { _, _ ->
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


