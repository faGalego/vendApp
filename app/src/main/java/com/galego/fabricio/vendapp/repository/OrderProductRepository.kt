package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity
import com.galego.fabricio.vendapp.data.db.wrapper.OrderProduct

interface OrderProductRepository {

    suspend fun insert(vararg orderProductEntity: OrderProductEntity)

    suspend fun update(vararg orderProductEntity: OrderProductEntity)

    suspend fun delete(id: Long)

    suspend fun getByOrderId(orderId: Long): List<OrderProduct>
}