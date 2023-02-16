package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.entity.ProductEntity
import com.galego.fabricio.vendapp.data.db.wrapper.BestSeller

interface ProductRepository {

    suspend fun insertProduct(name: String, price: Double): Long

    suspend fun updateProduct(id: Long, name: String, price: Double)

    suspend fun deleteProduct(id: Long)

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun getProductById(id: Long): ProductEntity?

    suspend fun getCountProducts(): Int

    suspend fun getBestSeller(): BestSeller?
}