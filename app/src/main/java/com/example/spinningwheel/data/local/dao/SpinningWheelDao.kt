package com.example.spinningwheel.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.spinningwheel.data.local.model.SpinningWheelDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpinningWheelDao {
    @Query("SELECT * FROM wheel_data")
    fun getAllWheelData(): Flow<List<SpinningWheelDataEntity>>
    @Query("SELECT * FROM wheel_data WHERE id = (:id)")
    suspend fun getData(id: Int): SpinningWheelDataEntity
    @Insert
    suspend fun addNewWheelData(entity: SpinningWheelDataEntity)
    @Query("DELETE FROM wheel_data WHERE id = (:id)")
    suspend fun deleteWheelData(id: Int)
}