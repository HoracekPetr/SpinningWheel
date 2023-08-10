package com.example.spinningwheel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spinningwheel.core.presentation.util.WheelColorScheme

@Entity(tableName = "wheel_data")
data class SpinningWheelDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("wheel_title")
    val wheelTitle: String,
    @ColumnInfo("items")
    val items: String,
    @ColumnInfo("font_size")
    val wheelFontSize: Float,
    @ColumnInfo("color_scheme")
    val wheelColorScheme: WheelColorScheme,
)

data class SpinningWheelData(
    val id: Int = 0,
    val wheelTitle: String = "",
    val items: List<String> = listOf(),
    val wheelFontSize: Float = 16f,
    val wheelColorScheme: WheelColorScheme = WheelColorScheme.Classic,
)

fun SpinningWheelData.toEntity() = SpinningWheelDataEntity(
    wheelTitle = wheelTitle,
    items = items.joinToString(";"),
    wheelFontSize = wheelFontSize,
    wheelColorScheme = wheelColorScheme
)

fun SpinningWheelDataEntity.toDto() = SpinningWheelData(
    id = id,
    wheelTitle = wheelTitle,
    items = items.split(";"),
    wheelFontSize = wheelFontSize,
    wheelColorScheme = wheelColorScheme
)

fun List<SpinningWheelDataEntity>.toDto() = map { it.toDto() }
