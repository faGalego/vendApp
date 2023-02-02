package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.OrderProductDao
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity

class OrderProductRepositoryImpl(
    private val orderProductDao: OrderProductDao
) : OrderProductRepository {

    override suspend fun insert(
        orderId: Long,
        productId: Long,
        quantity: Long,
        price: Long,
        total: Long
    ): Long {
        val orderProduct = OrderProductEntity(
            orderId = orderId,
            productId = productId,
            quantity = quantity,
            price = price,
            total = total
        )
        return orderProductDao.insert(orderProduct)
    }

    override suspend fun update(
        id: Long,
        orderId: Long,
        productId: Long,
        quantity: Long,
        price: Long,
        total: Long
    ) {
        val orderProduct = OrderProductEntity(
            id = id,
            orderId = orderId,
            productId = productId,
            quantity = quantity,
            price = price,
            total = total
        )
        orderProductDao.update(orderProduct)
    }

    override suspend fun delete(id: Long) {
        orderProductDao.delete(id)
    }
}