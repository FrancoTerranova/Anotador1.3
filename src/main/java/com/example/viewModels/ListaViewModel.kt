package com.example.viewModels

import Entities.ItemLista
import Entities.Lista
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ListaViewModel : ViewModel() {

    var list : MutableLiveData<Lista> = MutableLiveData<Lista>(null)
    var items : MutableLiveData<ArrayList<ItemLista>> = MutableLiveData<ArrayList<ItemLista>>(null)
    var linlyout : Long = 0
    var totalitems : BigDecimal = BigDecimal.ZERO
    var itemAgregado = false
    var prueb : MutableLiveData<String> = MutableLiveData("")
    var decimales_valor : String = ""
    fun iniciar(){
        list.value = Lista(0,0,"","","")
        items.value = ArrayList<ItemLista>()
        totalitems = BigDecimal.ZERO
    }
    fun seTprueb(s : String){
        prueb.value = s
    }
    fun addItem(nombreIt : String, precio : BigDecimal){


        val itsaux = items.value
        val its = ItemLista(0,0,nombreIt,precio.toString())
        totalitems += precio
        itsaux?.add(its)
        items.value = itsaux

    }
    fun guardarDatos(nombreList : String, descrList : String?){
        val listi = list.value
        listi?.Nombre = nombreList
        listi?.Descripcion = descrList
        val a : LocalDate = LocalDate.now()
        listi?.DiaCreacion = a.dayOfMonth.toString()
        list.value = listi

    }
    fun limpiarDatos(){
        list.value = Lista(0,0,"","","")
        items.value = ArrayList<ItemLista>()
        totalitems = BigDecimal.ZERO
    }


}