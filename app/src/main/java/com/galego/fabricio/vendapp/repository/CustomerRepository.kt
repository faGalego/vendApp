package com.galego.fabricio.vendapp.repository

import androidx.room.Query
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import java.util.*

interface CustomerRepository {

    suspend fun insertCustomer(name: String, phone: String): Long

    suspend fun updateCustomer(id: Long, name: String, phone: String, createdAt: Date)

    suspend fun deleteCustomer(id: Long)

    suspend fun getAllCustomers(): List<CustomerEntity>

    suspend fun getCountCustomers(): Int

    suspend fun getCountNewCustomersByMonth(month: String): Int
}