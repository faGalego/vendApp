package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.OrderDao
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity

class OrderRepositoryImpl(
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun insertOrder(customerId: Long, total: Double): Long {
        val order = OrderEntity(customerId = customerId, total = total)
        return orderDao.insert(order)
    }

    override suspend fun updateOrder(id: Long, customerId: Long, total: Double) {
        val order = OrderEntity(customerId = customerId, total = total)
        orderDao.update(order)
    }

    override suspend fun deleteOrder(id: Long) {
        orderDao.delete(id)
    }

    override suspend fun getAllOrders(): List<OrderEntity> {
        return orderDao.getAll()
    }
}