package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity
import com.galego.fabricio.vendapp.data.db.wrapper.MonthWithTotal

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(orderEntity: OrderEntity): Long

    @Update
    suspend fun update(orderEntity: OrderEntity)

    @Query("DELETE FROM `order` WHERE id = :id")
    suspend fun delete(id: Long)

    @Query(
        """SELECT
        
            strftime('%Y-%m', createdAt/1000, 'unixepoch') as month, 
        SUM(total) as total
        from `order` 
        GROUP BY 1"""
    )
    suspend fun getTotalGroupingByDate(): List<MonthWithTotal?>

}
