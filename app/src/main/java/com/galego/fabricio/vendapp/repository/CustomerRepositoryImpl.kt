package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.CustomerDao
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import java.util.*

class CustomerRepositoryImpl(
    private val customerDao: CustomerDao
) : CustomerRepository {

    override suspend fun insertCustomer(name: String, phone: String): Long {
        val customer =
            CustomerEntity(name = name, phone = phone, createdAt = Calendar.getInstance().time)
        return customerDao.insert(customer)
    }

    override suspend fun updateCustomer(id: Long, name: String, phone: String, createdAt: Date) {
        val customer = CustomerEntity(id = id, name = name, phone = phone, createdAt = createdAt)
        customerDao.update(customer)
    }

    override suspend fun deleteCustomer(id: Long) {
        customerDao.delete(id)
    }

    override suspend fun getAllCustomers(): List<CustomerEntity> {
        return customerDao.getAll()
    }

    override suspend fun getCountCustomers(): Int {
        return customerDao.getCountCustomers()
    }

    override suspend fun getCountNewCustomersByMonth(month: String): Int {
        return customerDao.getCountNewCustomersByMonth(month)
    }


}