package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.OrderDao
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity
import java.util.*

class OrderRepositoryImpl(
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun insertOrder(customerId: Long, total: Double, createdAt: Date): Long {
        val order = OrderEntity(customerId = customerId, total = total, createdAt = createdAt)
        return orderDao.insert(order)
    }

    override suspend fun updateOrder(id: Long, customerId: Long, total: Double, createdAt: Date) {
        val order =
            OrderEntity(id = id, customerId = customerId, total = total, createdAt = createdAt)
        orderDao.update(order)
    }

    override suspend fun deleteOrder(id: Long) {
        orderDao.delete(id)
    }


}