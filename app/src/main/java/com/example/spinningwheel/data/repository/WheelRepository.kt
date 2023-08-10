package com.example.spinningwheel.data.repository

import com.example.spinningwheel.data.local.model.SpinningWheelData
import kotlinx.coroutines.flow.Flow

interface WheelRepository {
    fun getAllWheelData(): Flow<List<SpinningWheelData>>
    suspend fun getWheelData(id: Int): SpinningWheelData
    suspend fun insertWheelData(data: SpinningWheelData)
    suspend fun deleteWheelData(id: Int)
}