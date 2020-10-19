package com.example.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.classes.busquedaItems
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.collections.ArrayList

class BuscarItemViewModel : ViewModel() {
    var anios : List<Int>? = null
    val meseS :  ArrayList<String> = ArrayList()

    var periodo_opcion_selec = ""
    var mes_selec_hasta : String = ""
    var anio_selec_hasta : String = ""
    var dia_selec_hasta : String = ""
    var anio_selec : String = ""
    var mes_selec : String = ""
    var dia_selec : String = ""

    var valor_desde : String = ""
    var valor_hasta : String = ""
    var valor_unico : String = ""

    val busquedaConfig : MutableLiveData<busquedaItems> = MutableLiveData(busquedaItems())
    var busquedaconfigchange : MutableLiveData<Boolean> = MutableLiveData(false)
    fun configurarBusqueda(rango: String, rangoValor: String){
        val busq = busquedaConfig.value
        if(!rango.equals(""))
        busq?.setRango(rango)
        if(!rangoValor.equals(""))
        busq?.setValor(rangoValor)
        busquedaconfigchange.value = true
    }
    fun limpiarPeriodo(){
        val busq = busquedaConfig.value
        busq?.limpiarPeriodo()
        busquedaConfig.value = busq
    }
    fun setCualquierValor(){
        configurarBusqueda("","cualquier")
        setConfigChange(true)
    }
    fun setConfigChange(b : Boolean){
        busquedaconfigchange.value = b
    }

    fun setLast7Dias(){
        val a : LocalDate = LocalDate.now()

        val semanapasada = LocalDate.of(a.year,a.month,a.dayOfMonth).minusWeeks(1)

        val stB = StringBuilder()

        stB.append(semanapasada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        stB.append(",")
        stB.append(a.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))

        configurarBusqueda(stB.toString(), "")





    }
    fun setDiaDeLaFecha(){
        val a : LocalDate = LocalDate.now()
        val stB = StringBuilder()
         stB.append(a.dayOfMonth.toString())
        stB.append("/")

        stB.append(a.month.value.toString())
        stB.append("/")

        stB.append(a.year.toString())
        configurarBusqueda(stB.toString(),"")

    }
    fun nuevaBusqueda(){
        busquedaConfig.value = busquedaItems()
        busquedaconfigchange.value = false

    }

    fun setLastMonth(){
        dia_selec = "1"
        dia_selec_hasta = getMesAnteriorLength().toString()
        mes_selec = getMesAnterior().toString()
        mes_selec_hasta = mes_selec
        anio_selec = getCurrentYear().toString()
        anio_selec_hasta = anio_selec
        setRangoLastMonth()

    }

    fun setRangoLastMonth() {
        val rangbuild = StringBuilder()
        rangbuild.append(dia_selec)
        rangbuild.append("/")
        rangbuild.append(mes_selec)
        rangbuild.append("/")
        rangbuild.append(anio_selec)
        rangbuild.append(",")
        rangbuild.append(dia_selec_hasta)
        rangbuild.append("/")
        rangbuild.append(mes_selec_hasta)
        rangbuild.append("/")
        rangbuild.append(anio_selec_hasta)
        configurarBusqueda(rangbuild.toString(),"")
    }
    fun getMesAnterior() : Int {
        val a : LocalDate = LocalDate.now()

        return (a.month.value - 1)
    }
    fun getMesAnteriorLength() : Int{
        val a : LocalDate = LocalDate.now()

        val c = a.month.value - 1
        val d = a.year
        val yearMonthObject = YearMonth.of(d, c)
        return yearMonthObject.lengthOfMonth()
    }
    fun getCurrentYear() : Int {
        val a : LocalDate = LocalDate.now()
        return a.year
    }
    fun Mestonumber(Ms: String) : Int {
        var mesnumerito : Int = 0
        if (Ms.equals("Enero"))
            mesnumerito = 1
        else
            if (Ms.equals("Febrero"))
                mesnumerito = 2
            else
                if (Ms.equals("Marzo"))
                    mesnumerito = 3
                else
                    if (Ms.equals("Abril"))
                        mesnumerito = 4
                    else
                        if (Ms.equals("Mayo"))
                            mesnumerito = 5
                        else
                            if (Ms.equals("Junio"))
                                mesnumerito = 6
                            else
                                if (Ms.equals("Julio"))
                                    mesnumerito = 7
                                else
                                    if (Ms.equals("Agosto"))
                                        mesnumerito = 8
                                    else
                                        if (Ms.equals("Septiembre"))
                                            mesnumerito = 9
                                        else
                                            if (Ms.equals("Octubre"))
                                                mesnumerito = 10
                                            else
                                                if (Ms.equals("Noviembre"))
                                                    mesnumerito = 11
                                                else
                                                    if (Ms.equals("Diciembre"))
                                                        mesnumerito = 12

        return mesnumerito
    }
    fun getAnios() : ArrayList<Int>{
        val a : ArrayList<Int> = ArrayList()
        if(anios != null)
        for(i in anios!!){
            a.add(i)
        }
        return a
    }
    fun getMeses() : ArrayList<String>{

        if(meseS.size == 0) {

            meseS.add("Enero")
            meseS.add("Febrero")
            meseS.add("Marzo")
            meseS.add("Abril")
            meseS.add("Mayo")
            meseS.add("Junio")
            meseS.add("Julio")
            meseS.add("Agosto")
            meseS.add("Septiembre")
            meseS.add("Octubre")
            meseS.add("Noviembre")
            meseS.add("Diciembre")
        }
        return meseS

    }


    fun getDias(extremo: String) : ArrayList<Int>{
        val dias = ArrayList<Int>()
        var mesnumerito : Int = 0

        if(extremo == "D") {


            mesnumerito = Mestonumber(mes_selec)
            val yearMonthObject = YearMonth.of(anio_selec.toInt(), mesnumerito)
            val daysInMonth = yearMonthObject.lengthOfMonth()

            for (i in 1..daysInMonth) {
                dias.add(i)
            }
            return dias
        }
        else{

            mesnumerito = Mestonumber(mes_selec_hasta)


            val yearMonthObject = YearMonth.of(anio_selec_hasta.toInt(), mesnumerito)
            val daysInMonth = yearMonthObject.lengthOfMonth()


            for (i in 1..daysInMonth) {
                dias.add(i)
            }
            return dias
        }


    }

}