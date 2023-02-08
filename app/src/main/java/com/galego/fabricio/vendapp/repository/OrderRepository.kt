package com.galego.fabricio.vendapp.repository

interface OrderRepository {

    suspend fun insertOrder(customerId: Long, total: Double): Long

    suspend fun updateOrder(id: Long, customerId: Long, total: Double)

    suspend fun deleteOrder(id: Long)

}