package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity

@Dao
interface OrderProductDao {

    @Insert
    suspend fun insert(orderProductEntity: OrderProductEntity): Long

    @Update
    suspend fun update(orderProductEntity: OrderProductEntity)

    @Query("DELETE FROM order_product WHERE id = :id")
    suspend fun delete(id: Long)

}