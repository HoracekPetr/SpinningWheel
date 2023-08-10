package com.example.spinningwheel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spinningwheel.data.local.dao.SpinningWheelDao
import com.example.spinningwheel.data.local.model.SpinningWheelDataEntity

@Database(entities = [SpinningWheelDataEntity::class], version = 2)
abstract class WheelDatabase : RoomDatabase() {
    abstract fun dao(): SpinningWheelDao
}

const val DB_NAME = "Wheel-DB"