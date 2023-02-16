package com.galego.fabricio.vendapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity
import com.galego.fabricio.vendapp.data.db.wrapper.BestSeller

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

    @Query("SELECT COUNT(id) FROM product")
    suspend fun getCountProducts(): Int

    @Query(
        """
        SELECT p.id, p.name, count(o.id) AS count
        FROM product p
        INNER JOIN order_product o ON o.productId = p.id
        GROUP BY 1, 2
        ORDER BY 3 DESC
        LIMIT 1"""
    )
    suspend fun getBestSeller(): BestSeller?
}