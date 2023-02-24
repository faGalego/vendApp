package com.galego.fabricio.vendapp.di

import android.app.Application
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.data.db.dao.*
import com.galego.fabricio.vendapp.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getAppDB(context: Application): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun getCustomerDao(appDatabase: AppDatabase): CustomerDao {
        return appDatabase.customerDao
    }

    @Provides
    @Singleton
    fun getProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao
    }

    @Provides
    @Singleton
    fun getOrderDao(appDatabase: AppDatabase): OrderDao {
        return appDatabase.orderDao
    }

    @Provides
    @Singleton
    fun getOrderProductDao(appDatabase: AppDatabase): OrderProductDao {
        return appDatabase.orderProductDao
    }

    @Provides
    @Singleton
    fun getOrderCustomerDao(appDatabase: AppDatabase): OrderCustomerDao {
        return appDatabase.orderCustomerDao
    }

    @Provides
    @Singleton
    fun getCustomerRepository(dao: CustomerDao): CustomerRepository {
        return CustomerRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun getProductRepository(dao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun getOrderRepository(dao: OrderDao): OrderRepository {
        return OrderRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun getOrderCustomerRepository(dao: OrderCustomerDao): OrderCustomerRepository {
        return OrderCustomerRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun getOrderProductRepository(dao: OrderProductDao): OrderProductRepository {
        return OrderProductRepositoryImpl(dao)
    }


}