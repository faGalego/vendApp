package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.entity.ProductEntity

interface ProductRepository {

    suspend fun insertProduct(name: String, price: Double): Long

    suspend fun updateProduct(id: Long, name: String, price: Double)

    suspend fun deleteProduct(id: Long)

    suspend fun getAllProducts(): List<ProductEntity>
}