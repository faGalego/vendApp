package com.galego.fabricio.vendapp.di

import androidx.room.Room
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.repository.*
import com.galego.fabricio.vendapp.ui.customer.CustomerViewModel
import com.galego.fabricio.vendapp.ui.customerlist.CustomerListViewModel
import com.galego.fabricio.vendapp.ui.mainmenu.MainMenuViewModel
import com.galego.fabricio.vendapp.ui.order.OrderViewModel
import com.galego.fabricio.vendapp.ui.orderlist.OrderListViewModel
import com.galego.fabricio.vendapp.ui.product.ProductViewModel
import com.galego.fabricio.vendapp.ui.productlist.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "vendapp_database"
        ).build()
    }


    single { get<AppDatabase>().customerDao }

    single { get<AppDatabase>().productDao }

    single { get<AppDatabase>().orderDao }
    single { get<AppDatabase>().orderCustomerDao }
    single { get<AppDatabase>().orderProductDao }


    single<CustomerRepository> { CustomerRepositoryImpl(get()) }

    single<ProductRepository> { ProductRepositoryImpl(get()) }

    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<OrderCustomerRepository> { OrderCustomerRepositoryImpl(get()) }
    single<OrderProductRepository> { OrderProductRepositoryImpl(get()) }


    viewModel { MainMenuViewModel(get(), get(), get()) }

    viewModel { CustomerListViewModel(get()) }
    viewModel { CustomerViewModel(get()) }

    viewModel { ProductListViewModel(get()) }
    viewModel { ProductViewModel(get()) }

    viewModel { OrderListViewModel(get()) }
    viewModel { OrderViewModel(get(), get(), get(), get()) }


}