package com.example.basededatos.repository

import com.example.basededatos.data.Articulo
import com.example.basededatos.data.ArticuloDao
import kotlinx.coroutines.flow.Flow

class ArticuloRepository(
    private val dao: ArticuloDao
) {

    suspend fun insertar(articulo: Articulo) {
        dao.insertar(articulo)
    }

    suspend fun actualizar(articulo: Articulo): Int {
        return dao.actualizar(articulo)
    }

    suspend fun eliminarPorCodigo(codigo: Int): Int {
        return dao.eliminarPorCodigo(codigo)
    }

    suspend fun buscarPorCodigo(codigo: Int): Articulo? {
        return dao.buscarPorCodigo(codigo)
    }

    fun listarTodos(): Flow<List<Articulo>> {
        return dao.listarTodos()
    }
}