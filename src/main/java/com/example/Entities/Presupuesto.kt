package Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Presupuesto (
    @PrimaryKey
    var presupuestoID : Long,
    var Cantidad : Long
)