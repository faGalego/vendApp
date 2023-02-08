package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity

@Dao
interface OrderProductDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg orderProductEntity: OrderProductEntity)

    @Update(onConflict = REPLACE)
    suspend fun update(vararg orderProductEntity: OrderProductEntity)

    @Query("DELETE FROM order_product WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM order_product WHERE orderId = :orderId")
    suspend fun getByOrderId(orderId: Long): List<OrderProductEntity>
}