package com.galego.fabricio.vendapp.repository

interface OrderProductRepository {

    suspend fun insert(
        orderId: Long,
        productId: Long,
        quantity: Long,
        price: Long,
        total: Long
    ): Long

    suspend fun update(
        id: Long,
        orderId: Long,
        productId: Long,
        quantity: Long,
        price: Long,
        total: Long
    )

    suspend fun delete(id: Long)
}