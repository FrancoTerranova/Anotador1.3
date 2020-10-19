package Entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Lista_items (
    @Embedded var lista  : Lista,
    @Relation(
        parentColumn = "ListaID",
        entityColumn = "ListaID",


    )
    val items : List<ItemLista>
)