package com.osvaldo.stickerstracker.utils

import android.app.Application
import com.osvaldo.stickerstracker.di.appModule
import com.osvaldo.stickerstracker.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StickerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StickerApplication)
            modules(appModule, dataModule)
        }
    }
}

