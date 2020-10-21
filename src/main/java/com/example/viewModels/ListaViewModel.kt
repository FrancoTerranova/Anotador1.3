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
    var editar_item : MutableLiveData<Boolean> = MutableLiveData(false)
    var item_a_editar : ItemLista? = null
    var linlyout : Long = 0
    var totalitems : Double = 0.0
    var itemAgregado = false
    var prueb : MutableLiveData<String> = MutableLiveData("")
    var decimales_valor : String = ""
    fun iniciar(){
        list.value = Lista(0,0,"","","")
        items.value = ArrayList<ItemLista>()
        totalitems = 0.0
        prueb.value = ""

    }
    fun seTprueb(s : String){
        prueb.value = s
    }
    fun itemAEditar(b : Boolean){
        editar_item.value = b

    }
    fun editarItem(nuevoNom : String, nuevoVal : Double){
        val itemsitos = items.value
        for(ims in itemsitos!!){
            if(ims.NombreItem.equals(item_a_editar?.NombreItem) && ims.Precio == item_a_editar?.Precio){
                ims.NombreItem = nuevoNom
                ims.Precio = nuevoVal
            }
        }
        items.value = itemsitos

    }
    fun reiniciarItemAEdit(){

            item_a_editar = null
    }
    fun getAgregados() : ArrayList<ItemLista>?{

        val its = items.value
        if(its != null)
        return its
        else
            return null
    }
    fun getAgregadosSize() : Int {
        val its = items.value
        if(its != null)
            return its.size
        else
            return -1
    }
    fun addItem(nombreIt : String, precio : Double){


        val itsaux = items.value
        val its = ItemLista(0,0,nombreIt,precio)
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
        totalitems = 0.0
        prueb.value = ""
    }


}