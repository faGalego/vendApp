package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.OrderProductDao
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity

class OrderProductRepositoryImpl(
    private val orderProductDao: OrderProductDao
) : OrderProductRepository {

    override suspend fun insert(vararg orderProductEntity: OrderProductEntity) {
        orderProductDao.insert(*orderProductEntity)
    }

    override suspend fun update(vararg orderProductEntity: OrderProductEntity) {
        orderProductDao.update(*orderProductEntity)
    }

    override suspend fun delete(id: Long) {
        orderProductDao.delete(id)
    }

    override suspend fun getByOrderId(orderId: Long): List<OrderProductEntity> {
        return orderProductDao.getByOrderId(orderId)
    }
}