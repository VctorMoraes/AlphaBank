package com.victor.alphabank.domain

import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import kotlinx.coroutines.flow.Flow

interface GetLocalLoanResultUseCase {
    operator fun invoke(): Flow<LoanResultModel>
}