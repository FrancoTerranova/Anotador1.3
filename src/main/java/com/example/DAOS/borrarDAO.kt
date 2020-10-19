package com.example.DAOS

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface borrarDAO {

    @Query("DELETE FROM Lista")
    fun borrarListas()

    @Query("DELETE FROM GastoMensual")
    fun borrarGastoMensual()

    @Query("DELETE FROM ItemLista")
    fun borrarItemsLista()


    @Transaction
    fun borrartodo(){
        borrarItemsLista()
        borrarListas()
        borrarGastoMensual()
    }
}