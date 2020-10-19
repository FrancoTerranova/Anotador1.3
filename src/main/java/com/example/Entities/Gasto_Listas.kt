package Entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Gasto_Listas (
    @Embedded val gastoMensual : GastoMensual,
    @Relation(
        parentColumn = "gastoMensualID",
        entityColumn = "gastoMensualID",


    )
    val Listas : List<Lista>
)