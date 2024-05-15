package com.victor.alphabank.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoanResultEntity(
    @PrimaryKey
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "max_amount") var maxAmount: Float?
)