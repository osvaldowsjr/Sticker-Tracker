package com.osvaldo.stickerstracker.di

import com.osvaldo.stickerstracker.appDatabase.NationsDatabase
import com.osvaldo.stickerstracker.data.dataSource.NationDataSource
import com.osvaldo.stickerstracker.data.dataSource.NationDataSourceImpl
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.data.repository.NationRepositoryImpl
import com.osvaldo.stickerstracker.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NationsDatabase.getDao(androidContext(), CoroutineScope(Dispatchers.IO)) }
}

val dataModule = module {
    single<NationDataSource> { NationDataSourceImpl(get()) }
    single<NationRepository> { NationRepositoryImpl(get()) }
    viewModel { MainViewModel(get()) }
}