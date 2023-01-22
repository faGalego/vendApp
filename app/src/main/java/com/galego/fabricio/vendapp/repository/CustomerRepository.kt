package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity

interface CustomerRepository {

    suspend fun insertCustomer(name: String, phone: String): Long

    suspend fun updateCustomer(id: Long, name: String, phone: String)

    suspend fun deleteCustomer(id: Long)

    suspend fun getAllCustomers(): List<CustomerEntity>
}