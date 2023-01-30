package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(orderEntity: OrderEntity): Long

    @Update
    suspend fun update(orderEntity: OrderEntity)

    @Query("DELETE FROM `order` WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM `order`")
    suspend fun getAll(): List<OrderEntity>
}
