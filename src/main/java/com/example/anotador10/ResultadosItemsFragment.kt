package com.example.anotador10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.DataBase.DbViewModel

class ResultadosItemsFragment : Fragment() {

    lateinit var dbVM : DbViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

         dbVM =  ViewModelProvider(requireActivity()).get(DbViewModel::class.java)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resultados_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ly = view.findViewById<LinearLayout>(R.id.items)

        val lista_items = dbVM.items_encontrados
        if(lista_items != null){
            for( i in lista_items){
                val txt = TextView(requireActivity())
                txt.setText((i.NombreItem + "  " + i.Precio))
                ly.addView(txt)
            }
        }
    }


}