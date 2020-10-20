package Entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(indices = [Index(value = ["Precio"])])
class ItemLista (
    @PrimaryKey
    var itemListaID : Long,
    var ListaID : Long,
    var NombreItem : String,
    var Precio : String
)