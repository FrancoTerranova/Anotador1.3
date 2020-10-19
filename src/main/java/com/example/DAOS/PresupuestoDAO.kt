package DAOS

import Entities.Presupuesto
import androidx.room.*

@Dao
interface PresupuestoDAO {

@Insert
fun addPresupuesto(p : Presupuesto)

    @Query("SELECT Cantidad FROM Presupuesto WHERE presupuestoID = :pID")
    fun getPresupuesto(pID : Long) : Long?


    @Update(entity = Presupuesto::class)
    fun updatePresupuesto(u : PresupuestoUpdate)

    @Entity
    data class PresupuestoUpdate (
        var presupuestoID : Long,
        var Cantidad : Long
    )
}