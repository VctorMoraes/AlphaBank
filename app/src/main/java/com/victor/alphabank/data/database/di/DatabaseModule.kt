package com.victor.alphabank.data.database.di

import android.content.Context
import androidx.room.Room
import com.victor.alphabank.data.database.AlphaBankDatabase
import com.victor.alphabank.data.database.dao.LoanResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): AlphaBankDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            AlphaBankDatabase::class.java,
            AlphaBankDatabase.DATABASE_NAME,
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesLoanRestultDao(
        database: AlphaBankDatabase,
    ): LoanResultDao = database.loanResultDao()
}