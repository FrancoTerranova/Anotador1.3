package DAOS

import Entities.GastoMensual
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GastoMensualDAO {
    @Insert
    fun addGasto(g : GastoMensual)

    @Query("SELECT * FROM GastoMensual WHERE GastoMensual.Anio = :anio AND GastoMensual.Mes = :mes")
    fun getGasto(anio : Int, mes : Int) : GastoMensual?
    @Query("SELECT * FROM GastoMensual WHERE GastoMensual.Anio = :anio AND GastoMensual.Mes = :mes")
    fun getGastoLD(anio : Int, mes : Int) : LiveData<GastoMensual?>
    @Query("SELECT MAX(gastoMensualID) FROM GastoMensual")
    fun getLastID() : Long?

    @Query("SELECT Anio FROM GastoMensual GROUP BY Anio ORDER BY Anio ASC")
    fun getanios() : LiveData<List<Int>>
}