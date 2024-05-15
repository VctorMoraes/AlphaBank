package com.victor.alphabank.domain.di

import com.victor.alphabank.domain.GetLocalLoanResultUseCase
import com.victor.alphabank.domain.GetLocalLoanResultUseCaseImpl
import com.victor.alphabank.domain.LoanRequestUseCase
import com.victor.alphabank.domain.LoanRequestUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindsLoanRequestUseCase(
        loanRequestUseCase: LoanRequestUseCaseImpl
    ): LoanRequestUseCase

    @Binds
    fun bindsGetLocalLoanResultUseCase(
        getLocalLoanRequestUseCase: GetLocalLoanResultUseCaseImpl
    ): GetLocalLoanResultUseCase

}