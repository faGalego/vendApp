package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.OrderProductDao
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity
import com.galego.fabricio.vendapp.data.db.wrapper.OrderProduct

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

    override suspend fun getByOrderId(orderId: Long): List<OrderProduct> {
        return orderProductDao.getByOrderId(orderId)
    }
}