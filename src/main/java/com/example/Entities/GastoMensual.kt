package Entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = ["Anio","Mes"])))
class GastoMensual (

    @PrimaryKey
    var gastoMensualID : Long,
    var Anio : Int,
    var Mes : Int


)

