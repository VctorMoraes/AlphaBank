package com.victor.alphabank.data.loanRequest.repository

import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.data.loanRequest.model.UserModel
import kotlinx.coroutines.flow.Flow

interface LoanRequestRepository {

    fun requestLoan(user: UserModel): Flow<LoanResultModel>

    fun getLocalLoanResult(): Flow<LoanResultModel>
}