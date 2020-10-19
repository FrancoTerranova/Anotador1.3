package DAOS

import Entities.Lista
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ListaDAO {

    @Insert
    fun addLista(l : Lista)
    @Query("SELECT MAX(ListaID) FROM Lista")
    fun getLastID() : Long?

}