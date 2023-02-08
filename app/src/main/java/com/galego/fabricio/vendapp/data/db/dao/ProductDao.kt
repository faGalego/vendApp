package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(productEntity: ProductEntity): Long

    @Update
    suspend fun update(productEntity: ProductEntity)

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getById(id: Long): ProductEntity?
}