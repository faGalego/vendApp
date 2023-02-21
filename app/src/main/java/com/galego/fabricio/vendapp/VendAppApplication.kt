package com.galego.fabricio.vendapp

import android.app.Application
import com.galego.fabricio.vendapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VendAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VendAppApplication)
            modules(appModule)
        }
    }
}