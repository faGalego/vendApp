package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.galego.fabricio.vendapp.data.db.wrapper.OrderCustomer

@Dao
interface OrderCustomerDao {

    @Query(
        """SELECT         
            customer.id AS customerId,
            customer.name AS customerName,
            `order`.id AS orderId,
            `order`.total AS orderTotal,
            `order`.createdAt
        FROM `order`
        INNER JOIN customer ON customer.id = `order`.customerId"""
    )
    suspend fun getAll(): List<OrderCustomer>

}