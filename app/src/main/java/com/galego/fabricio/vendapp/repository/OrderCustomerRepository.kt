package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.wrapper.OrderCustomer

interface OrderCustomerRepository {

    suspend fun getAllOrders(): List<OrderCustomer>

}