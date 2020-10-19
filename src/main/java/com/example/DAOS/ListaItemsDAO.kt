package DAOS

import Entities.*
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ListaItemsDAO {
    @Transaction
    @Query("SELECT * FROM Lista")
    fun getListaWithItems() : LiveData<List<Lista_items>>



    @Query("SELECT * FROM ItemLista WHERE ItemLista.ListaID = :lID")
    fun getItemofLista(lID : Long) : ItemLista?

    @Insert
    fun addLista(l : Lista)
    @Insert
    fun addItem(i : ItemLista)

    @Query("SELECT MAX(itemListaID) FROM ItemLista")
    fun getLastItemID() : Long?


    @Transaction
    fun AddListAndItems(l : Lista, its : ArrayList<ItemLista>){

        addLista(l)
        for(i in its){
            i.ListaID = l.ListaID
            addItem(i)
        }
    }
}