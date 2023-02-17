package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity

@Dao
interface CustomerDao {

    @Insert
    suspend fun insert(customerEntity: CustomerEntity): Long

    @Update
    suspend fun update(customerEntity: CustomerEntity)

    @Query("DELETE FROM customer WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM customer")
    suspend fun getAll(): List<CustomerEntity>

    @Query("SELECT COUNT(id) FROM customer")
    suspend fun getCountCustomers(): Int

    @Query("SELECT COUNT(id) FROM customer WHERE strftime('%m', createdAt/1000, 'unixepoch') = :month")
    suspend fun getCountNewCustomersByMonth(month: String): Int
}