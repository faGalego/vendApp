package com.galego.fabricio.vendapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galego.fabricio.vendapp.data.db.dao.CustomerDao
import com.galego.fabricio.vendapp.data.db.dao.OrderDao
import com.galego.fabricio.vendapp.data.db.dao.ProductDao
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity

@Database(entities = [ProductEntity::class, CustomerEntity::class, OrderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
    abstract val customerDao: CustomerDao
    abstract val orderDao: OrderDao

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