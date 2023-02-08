package com.galego.fabricio.vendapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "vendapp_database"
                    ).build()
                }
                return instance
            }
        }
    }

}