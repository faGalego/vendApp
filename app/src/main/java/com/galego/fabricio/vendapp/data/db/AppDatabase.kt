package com.galego.fabricio.vendapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.dao.*
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity

@Database(
    entities = [CustomerEntity::class, ProductEntity::class, OrderEntity::class, OrderProductEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val customerDao: CustomerDao
    abstract val productDao: ProductDao
    abstract val orderDao: OrderDao
    abstract val orderProductDao: OrderProductDao
    abstract val orderCustomerDao: OrderCustomerDao

}