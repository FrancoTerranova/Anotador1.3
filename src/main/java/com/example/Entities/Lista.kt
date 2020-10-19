package Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Lista (
    @PrimaryKey
    var ListaID : Long,
    var gastoMensualID : Long,
    var Nombre : String,
    var Descripcion : String?,
    var DiaCreacion : String
)