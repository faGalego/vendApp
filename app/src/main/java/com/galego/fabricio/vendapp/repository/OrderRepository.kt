package com.galego.fabricio.vendapp.repository

import java.util.*

interface OrderRepository {

    suspend fun insertOrder(customerId: Long, total: Double, createdAt: Date): Long

    suspend fun updateOrder(id: Long, customerId: Long, total: Double, createdAt: Date)

    suspend fun deleteOrder(id: Long)

}