package com.example.basededatos.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticuloDao {

    @Insert
    suspend fun insertar(articulo: Articulo)

    @Update
    suspend fun actualizar(articulo: Articulo): Int

    @Query("SELECT * FROM articulos WHERE codigo = :codigo")
    suspend fun buscarPorCodigo(codigo: Int): Articulo?

    @Query("DELETE FROM articulos WHERE codigo = :codigo")
    suspend fun eliminarPorCodigo(codigo: Int): Int

    @Query("SELECT * FROM articulos")
    fun listarTodos(): Flow<List<Articulo>>
}