package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.OrderCustomerDao
import com.galego.fabricio.vendapp.data.db.wrapper.OrderCustomer

class OrderCustomerRepositoryImpl(
    private val dao: OrderCustomerDao
) : OrderCustomerRepository {

    override suspend fun getAllOrders(): List<OrderCustomer> {
        return dao.getAll()
    }
}