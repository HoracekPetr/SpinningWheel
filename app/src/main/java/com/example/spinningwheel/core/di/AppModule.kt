package com.example.spinningwheel.core.di

import androidx.room.Room
import com.example.spinningwheel.data.local.dao.SpinningWheelDao
import com.example.spinningwheel.data.local.db.DB_NAME
import com.example.spinningwheel.data.local.db.WheelDatabase
import com.example.spinningwheel.data.repository.WheelRepository
import com.example.spinningwheel.data.repository.WheelRepositoryImpl
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            WheelDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single {
        val db = get<WheelDatabase>()
        db.dao()
    }

    single<WheelRepository> {
        WheelRepositoryImpl(get())
    }

    viewModel { SpinningWheelViewModel(get()) }
}