package com.victor.alphabank.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victor.alphabank.data.database.dao.LoanResultDao
import com.victor.alphabank.data.database.entities.LoanResultEntity

@Database(version = 1, entities = [LoanResultEntity::class], exportSchema = false)
abstract class AlphaBankDatabase : RoomDatabase() {
    abstract fun loanResultDao(): LoanResultDao

    companion object {
        const val DATABASE_NAME = "AlphaBank"
    }
}