package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.entity.OrderEntity

interface OrderRepository {

    suspend fun insertOrder(customerId: Long, total: Double): Long

    suspend fun updateOrder(id: Long, customerId: Long, total: Double)

    suspend fun deleteOrder(id: Long)

    suspend fun getAllOrders(): List<OrderEntity>
}