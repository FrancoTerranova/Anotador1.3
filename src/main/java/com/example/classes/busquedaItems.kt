package com.example.classes

class busquedaItems {

    var rngtxt : String = ""
    var valoRtxt : String = ""

    var diaDesde : String = ""
    var mesDesde : String = ""
    var anioDesde : String = ""

    var diaHasta : String = ""
    var mesHasta : String = ""
    var anioHasta : String = ""

    var valorDesde : Long = 0
    var valorHasta : Long = 0

    var diaUnico : String = ""
    var mesUnico : String = ""
    var anioUnico : String = ""

    var unicoValor : Long = 0

    var isRango : Boolean = false
    var isRangoValor : Boolean = false
    var valorCualquier : Boolean = false
    var valorUnico : Boolean = false

    fun limpiarPeriodo(){
        diaDesde = ""
        mesDesde = ""
        anioDesde = ""
        diaHasta = ""
        mesHasta = ""
        anioHasta = ""
        diaUnico  = ""
        mesUnico = ""
        anioUnico = ""
        isRango = false
        rngtxt = ""
    }
    // el rango ira separado por ,
    fun setRango(ran : String){
        if(ran != "") {
            rngtxt = ran
            if (ran.contains(",")) {
                val rangodesde = ran.substringBefore(",")
                val rangohasta = ran.substringAfter(",")

                diaDesde = rangodesde.substringBefore("/")
                mesDesde = rangodesde.substringAfter("/").substringBeforeLast("/")
                anioDesde = rangodesde.substringAfterLast("/")

                diaHasta = rangohasta.substringBefore("/")
                mesHasta = rangohasta.substringAfter("/").substringBeforeLast("/")
                anioHasta = rangohasta.substringAfterLast("/")
                isRango = true
            } else {
                diaUnico = ran.substringBefore("/")
                mesUnico = ran.substringAfter("/").substringBeforeLast("/")
                anioUnico = ran.substringAfterLast("/")

            }
        }

    }
    fun setValor(ran : String){

        if(ran != "") {
            valoRtxt = ran
            if (ran.contains("-")) {
                val valorDsd = ran.substringBefore("-").toLong()
                val valorhst = ran.substringAfter("-").toLong()

                valorDesde = valorDsd
                valorHasta = valorhst

                isRangoValor = true
                valorCualquier = false


            }
            else
                if(ran.equals("cualquier")){
                    valorCualquier = true
                    isRangoValor = false

                }
            else {
                unicoValor = ran.toLong()
                    valorUnico = true
                    isRangoValor = false
                    valorCualquier = false
            }
        }
    }



}