package com.victor.alphabank.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victor.alphabank.data.database.entities.LoanResultEntity

@Dao
interface LoanResultDao {

    @Query("SELECT * FROM LoanResultEntity")
    fun getAll(): List<LoanResultEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg loanResult: LoanResultEntity)
}