package com.victor.alphabank.data.loanRequest.di

import com.victor.alphabank.data.loanRequest.datasource.remote.api.LoanRequestApi
import com.victor.alphabank.data.loanRequest.repository.LoanRequestRepository
import com.victor.alphabank.data.loanRequest.repository.LoanRequestRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsLoanRequestRepository(
        loanRequestRepository: LoanRequestRepositoryImpl
    ): LoanRequestRepository

    companion object {

        @Singleton
        @Provides
        fun providesLoanRequestApi(retrofit: Retrofit): LoanRequestApi = retrofit.create(
            LoanRequestApi::class.java
        )
    }
}