package com.victor.alphabank.domain

import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import kotlinx.coroutines.flow.Flow

interface LoanRequestUseCase {
    operator fun invoke(
        name: String,
        age: Int,
        monthIncome: Float,
        city: String
    ): Flow<LoanResultModel>
}