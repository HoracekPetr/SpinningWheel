package com.example.spinningwheel.data.repository

import com.example.spinningwheel.data.local.dao.SpinningWheelDao
import com.example.spinningwheel.data.local.model.SpinningWheelData
import com.example.spinningwheel.data.local.model.toDto
import com.example.spinningwheel.data.local.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WheelRepositoryImpl(
    private val dao: SpinningWheelDao
) : WheelRepository {

    override fun getAllWheelData(): Flow<List<SpinningWheelData>> {
        return dao.getAllWheelData().map { it.toDto() }
    }

    override suspend fun insertWheelData(data: SpinningWheelData) {
        return dao.addNewWheelData(data.toEntity())
    }

    override suspend fun getWheelData(id: Int): SpinningWheelData {
        return dao.getData(id).toDto()
    }

    override suspend fun deleteWheelData(id: Int) {
        return dao.deleteWheelData(id)
    }
}