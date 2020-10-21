package com.example.DAOS

import Entities.ItemLista
import Entities.Lista_items
import androidx.room.Dao
import androidx.room.Query
import java.math.BigDecimal

@Dao
interface busquedaItemsDAO {


    @Query("SELECT * FROM ItemLista " +
            "INNER JOIN Lista ON ((ItemLista.ListaID = Lista.ListaID) AND (Lista.DiaCreacion BETWEEN :diaDesde AND :diaHasta ))" +
            "INNER JOIN GastoMensual ON ((Lista.gastoMensualID = GastoMensual.gastoMensualID) AND (GastoMensual.Mes BETWEEN :mesDesde AND :mesHasta) AND (GastoMensual.Anio BETWEEN :anioDesde AND :anioHasta))" +
            "WHERE ItemLista.Precio BETWEEN :valorDesde AND :valorHasta"

    )
    fun buscarAmbosPorRango(
            diaDesde : String,
            mesDesde : String,
            anioDesde : String,
            diaHasta : String,
            mesHasta : String,
            anioHasta : String,
            valorDesde : Double,
            valorHasta : Double

    ): List<ItemLista>?

    @Query("SELECT * FROM ItemLista " +
            "INNER JOIN Lista ON ((ItemLista.ListaID = Lista.ListaID) AND (Lista.DiaCreacion = :diaUnico))" +
            "INNER JOIN GastoMensual ON ((Lista.gastoMensualID = GastoMensual.gastoMensualID) AND (GastoMensual.Mes = :mesUnico) AND (GastoMensual.Anio = :anioUnico))" +
            "WHERE ItemLista.Precio BETWEEN :valorDesde AND :valorHasta"

    )

    fun buscarPeriodoSimpleValorRango(
            diaUnico : String,
            mesUnico : String,
            anioUnico : String,
            valorDesde : Double,
            valorHasta : Double

    ) : List<ItemLista>?

    @Query("SELECT * FROM ItemLista " +
            "INNER JOIN Lista ON ((ItemLista.ListaID = Lista.ListaID) AND (Lista.DiaCreacion = :diaUnico))" +
            "INNER JOIN GastoMensual ON ((Lista.gastoMensualID = GastoMensual.gastoMensualID) AND (GastoMensual.Mes = :mesUnico) AND (GastoMensual.Anio = :anioUnico))"
    )
    fun buscarValorCualquierPeriodoSimple(diaUnico : String, mesUnico : String, anioUnico: String) : List<ItemLista>?

    @Query("SELECT * FROM ItemLista " +
            "INNER JOIN Lista ON ((ItemLista.ListaID = Lista.ListaID) AND (Lista.DiaCreacion BETWEEN :diaDesde AND :diaHasta ))" +
            "INNER JOIN GastoMensual ON ((Lista.gastoMensualID = GastoMensual.gastoMensualID) AND (GastoMensual.Mes BETWEEN :mesDesde AND :mesHasta) AND (GastoMensual.Anio BETWEEN :anioDesde AND :anioHasta))"
    )
    fun buscar7diasCualquierValor(
            diaDesde : String,
            mesDesde : String,
            anioDesde : String,
            diaHasta : String,
            mesHasta : String,
            anioHasta : String,
            ) : List<ItemLista>?


    @Query("SELECT * FROM ItemLista " +
            "INNER JOIN Lista ON ((ItemLista.ListaID = Lista.ListaID) AND (Lista.DiaCreacion BETWEEN :diaDesde AND :diaHasta ))" +
            "INNER JOIN GastoMensual ON ((Lista.gastoMensualID = GastoMensual.gastoMensualID) AND (GastoMensual.Mes BETWEEN :mesDesde AND :mesHasta) AND (GastoMensual.Anio BETWEEN :anioDesde AND :anioHasta))"
            + "WHERE ItemLista.Precio = :unicoValor"
    )
    fun buscarPeriodoRangoUnicoValor(
            diaDesde : String,
            mesDesde : String,
            anioDesde : String,
            diaHasta : String,
            mesHasta : String,
            anioHasta : String,
            unicoValor : Double

    ) : List<ItemLista>?

    @Query("SELECT * FROM ItemLista " +
            "INNER JOIN Lista ON ((ItemLista.ListaID = Lista.ListaID) AND (Lista.DiaCreacion = :diaUnico))" +
            "INNER JOIN GastoMensual ON ((Lista.gastoMensualID = GastoMensual.gastoMensualID) AND (GastoMensual.Mes = :mesUnico) AND (GastoMensual.Anio = :anioUnico))" +
            "WHERE ItemLista.Precio = :unicoValor"

    )
    fun buscarPeriodoSimpleValorUnico(
            diaUnico : String,
            mesUnico : String,
            anioUnico : String,
            unicoValor : Double

    ) : List<ItemLista>?
}