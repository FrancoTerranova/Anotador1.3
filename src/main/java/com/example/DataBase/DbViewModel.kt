package com.example.DataBase

import DataBase.AnotadorDB
import Entities.GastoMensual
import Entities.ItemLista
import Entities.Lista
import Entities.Lista_items
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.classes.busquedaItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class DbViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var listas: LiveData<List<Lista_items>>
    lateinit var  gastoCurrent : LiveData<GastoMensual?>
    lateinit var  anios_list : LiveData<List<Int>>
     var items_encontrados : List<ItemLista>? = null
    var items_encontrados_ready : MutableLiveData<Boolean> = MutableLiveData(false)
    var gastoCurrentInit : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var AniosListinit : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val db: AnotadorDB = AnotadorDB.getInstance(application)


    fun getDatos() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                listas = db.listaItemDAO().getListaWithItems()
                val cal = Calendar.getInstance()
                val fech = SimpleDateFormat("dd MM yyyy", Locale.forLanguageTag("es-ES"))
                        .format(java.util.Date())

                gastoCurrent = db.gastoMensualDAO().getGastoLD(fech.substringAfterLast(" ").toInt(), fech.substringAfter(" ").substringBeforeLast(" ").toInt())

                anios_list = db.gastoMensualDAO().getanios()



                }
            gastoCurrentInit.value = true
            AniosListinit.value = true
            }

    }

    fun setItemReady(v: Boolean){
        items_encontrados_ready.value = v
    }

    fun addGastoMensual() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                var last_id: Long? = db.gastoMensualDAO().getLastID()
                if (last_id == null) {
                    last_id = 0
                } else {
                    last_id++
                }

                val a : LocalDate = LocalDate.now()

                val c = a.month.value
                val d = a.year
                val g: GastoMensual = GastoMensual(last_id, d,c)
                val isin = db.gastoMensualDAO().getGasto(g.Anio, g.Mes)
                if (isin == null) {
                    db.gastoMensualDAO().addGasto(g)
                }

            }
        }

    }
    fun borrarTodo(){
        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                db.borrarDAO().borrartodo()
            }
            }
    }

    fun buscarItems(busq: busquedaItems) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                items_encontrados = null

                // rango - rango
                if (busq.isRango && busq.isRangoValor) {
                    items_encontrados = db.busquedaItemsDAO().buscarAmbosPorRango(
                            busq.diaDesde,
                            busq.mesDesde,
                            busq.anioDesde,
                            busq.diaHasta,
                            busq.mesHasta,
                            busq.anioHasta,
                            busq.valorDesde.toString(),
                            busq.valorHasta.toString()

                    )
                }


                else
                        // rango - cualquier
                        if (busq.isRango && busq.valorCualquier) {
                            items_encontrados = db.busquedaItemsDAO().buscar7diasCualquierValor(
                                    busq.diaDesde,
                                    busq.mesDesde,
                                    busq.anioDesde,
                                    busq.diaHasta,
                                    busq.mesHasta,
                                    busq.anioHasta,


                            )
                        }
                else
                            if(busq.isRango && busq.valorUnico){
                                items_encontrados = db.busquedaItemsDAO().buscarPeriodoRangoUnicoValor(
                                        busq.diaDesde,
                                        busq.mesDesde,
                                        busq.anioDesde,
                                        busq.diaHasta,
                                        busq.mesHasta,
                                        busq.anioHasta,
                                        busq.unicoValor.toString()

                                        )


                            }
                else

                // no rango - rango
                if(!busq.isRango && busq.isRangoValor){
                    items_encontrados = db.busquedaItemsDAO().buscarPeriodoSimpleValorRango(
                            busq.diaUnico,
                            busq.mesUnico,
                            busq.anioUnico,
                            busq.valorDesde.toString(),
                            busq.valorHasta.toString()

                    )
                }
                else
                // no rango - cualquier
                    if((!busq.isRango) && busq.valorCualquier){
                        items_encontrados = db.busquedaItemsDAO().buscarValorCualquierPeriodoSimple(
                                busq.diaUnico,
                                busq.mesUnico,
                                busq.anioUnico

                        )
                    }
                else
                        if((!busq.isRango) && busq.valorUnico){
                            items_encontrados = db.busquedaItemsDAO().buscarPeriodoSimpleValorUnico(
                                    busq.diaUnico,
                                    busq.mesUnico,
                                    busq.anioUnico,
                                    busq.unicoValor.toString()
                            )
                        }

            }
            items_encontrados_ready.value = true
        }
    }

    fun guardarLista(list: Lista, items: ArrayList<ItemLista>){
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                var last_id: Long? = db.listaDAO().getLastID()
                if (last_id == null) {
                    last_id = 0
                } else {
                    last_id++
                }
                var itlastid = db.listaItemDAO().getLastItemID()
                if (itlastid == null) {
                    itlastid = 0
                } else {
                    itlastid++
                }
                list.ListaID = last_id


                val gc = gastoCurrent.value
                if(gc != null)
                list.gastoMensualID = gc.gastoMensualID

                for(its in items){

                    its.itemListaID = itlastid
                    itlastid++
                }
                db.listaItemDAO().AddListAndItems(list, items)

                 }
            }
    }

}