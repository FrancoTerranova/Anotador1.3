package DataBase

import DAOS.*
import Entities.GastoMensual
import Entities.ItemLista
import Entities.Lista
import Entities.Presupuesto
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.DAOS.borrarDAO
import com.example.DAOS.busquedaItemsDAO

@Database(
    entities =
    [
        GastoMensual::class,
        Lista::class,
        ItemLista::class,
         Presupuesto::class

    ],
    version = 5,
    exportSchema = false

)
abstract class AnotadorDB : RoomDatabase() {
    companion object{
        @Volatile
        private var Instance : AnotadorDB? = null
        fun getInstance(context : Context) : AnotadorDB {
            val tempInstance = Instance

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnotadorDB::class.java,
                    "AnotadorDB"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Instance = instance
                return instance
            }
        }
    }
    abstract fun gastoListaDAO() : GastoListaDAO
    abstract fun gastoMensualDAO() : GastoMensualDAO
    abstract fun listaDAO() : ListaDAO
    abstract fun listaItemDAO() : ListaItemsDAO
    abstract fun presupuestoDAO() : PresupuestoDAO
    abstract fun borrarDAO() : borrarDAO
    abstract fun busquedaItemsDAO() : busquedaItemsDAO
}