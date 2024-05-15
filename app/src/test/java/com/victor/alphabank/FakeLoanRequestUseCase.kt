package com.victor.alphabank

import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.domain.LoanRequestUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLoanRequestUseCase: LoanRequestUseCase {

    override fun invoke(
        name: String,
        age: Int,
        monthIncome: Float,
        city: String
    ): Flow<LoanResultModel> = flow {
        emit(FAKE_REQUEST_MODEL)
    }

    companion object {
        val FAKE_REQUEST_MODEL = LoanResultModel("APPROVED", 2500F)
    }
}