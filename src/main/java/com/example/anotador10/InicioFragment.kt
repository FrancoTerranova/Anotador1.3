package com.example.anotador10

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.DataBase.DbViewModel
import com.example.viewModels.ControlViewModel
import com.example.anotador10.databinding.FragmentInicioBinding

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class InicioFragment : Fragment() {


    lateinit var inicioBinding : FragmentInicioBinding
    lateinit var controlVM : ControlViewModel
    lateinit var  dbVM : DbViewModel
    var si = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Bindings(inflater,container)

        inicioBinding.control = controlVM
        val view : View = inicioBinding.root






        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbVM.getDatos()

        Observar(view)

       // (context as MainActivity).topAppBar.setNavigationIcon(R.drawable.ic_baseline_menu_25)




    }
    fun Observar(viewGlobal : View){
        //dbVM.addGastoMensual()
        //dbVM.borrarTodo()
        controlVM.navegar.observe(viewLifecycleOwner, Observer { nav ->
            if(nav){
                controlVM.setNavegar(false)
                controlVM.idBoton.observe(viewLifecycleOwner, Observer {id ->

                    if(id.equals(getString(R.string.navdatosMensuales))){
                        controlVM.setidButton("")
                        Navigation.findNavController(viewGlobal).navigate(R.id.action_inicioFragment_to_datosMensualesFragment)
                    }
                    if(id.equals(getString(R.string.navNuevaLista))){
                        controlVM.setidButton("")
                        Navigation.findNavController(viewGlobal).navigate(R.id.action_inicioFragment_to_nuevaListaFragment)
                    }
                    if(id.equals(getString(R.string.navVerListas))){
                        if(si) {
                            controlVM.setidButton("")
                            Navigation.findNavController(viewGlobal).navigate(R.id.action_inicioFragment_to_verListasFragment)
                        }
                    }
                })
            }
        })
        controlVM.accion.observe(viewLifecycleOwner, Observer { act ->
            if(act.equals("nuevoMes")){
                controlVM.setAccion("")



                dbVM.addGastoMensual()
            }
        })
        dbVM.gastoCurrentInit.observe(viewLifecycleOwner, Observer { gcI ->
            if(gcI){
                dbVM.gastoCurrent.observe(viewLifecycleOwner, Observer { gc ->
                    if(gc != null){
                        val txtv : TextView = viewGlobal.findViewById(R.id.gastoFecha)
                        val st : StringBuilder = StringBuilder()
                        st.append("Gasto del mes\n")
                        st.append(gc.Mes)
                        st.append("/")
                        st.append(gc.Anio)
                        txtv.text = st
                    }
                })
            }
            dbVM.AniosListinit.observe(viewLifecycleOwner, Observer { aniosI ->
                if(aniosI){
                    si = true
                }
            })
        })

    }
    fun Bindings(inflater : LayoutInflater, container : ViewGroup?){
        inicioBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_inicio, container, false )
        controlVM = ViewModelProvider(requireActivity()).get(ControlViewModel::class.java)
        controlVM.iniciarDatos()
        dbVM = ViewModelProvider(requireActivity()).get(DbViewModel::class.java)
    }


}