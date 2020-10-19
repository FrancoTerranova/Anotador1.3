package DAOS

import Entities.*
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GastoListaDAO {


    @Transaction
    @Query("SELECT * FROM GastoMensual")
    fun getGastoWithListas() : LiveData<List<Gasto_Listas>>



   @Query("SELECT * FROM Lista WHERE Lista.gastoMensualID = :gID")
    fun getListaOfGasto(gID : Long) : Lista?

    @Query("SELECT MAX(itemListaID) FROM ItemLista")
    fun getLastID() : Long?

    @Insert
    fun addGasto(g : GastoMensual)

    @Insert
    fun addLista( l : Lista)

    @Insert
    fun addItem(i : ItemLista)

    @Transaction
    fun AddGastoAndListas(g : GastoMensual?, l : ArrayList<Lista>?, i : ArrayList<ItemLista>?){
        if(g != null) {
            addGasto(g)
        }
        if(l != null) {
            for (lis in l) {
                addLista(lis)
            }
        }
        if(i != null){
            for(its in i) {
                addItem(its)
            }
        }
    }


}