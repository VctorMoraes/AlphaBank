package com.victor.alphabank.domain

import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.data.loanRequest.model.UserModel
import com.victor.alphabank.data.loanRequest.repository.LoanRequestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoanRequestUseCaseImpl @Inject constructor(
    private val loanRequestRepository: LoanRequestRepository
) : LoanRequestUseCase {

    override fun invoke(
        name: String,
        age: Int,
        monthIncome: Float,
        city: String
    ): Flow<LoanResultModel> =
        loanRequestRepository.requestLoan(
            UserModel(
                name = name,
                age = age,
                monthIncome = monthIncome,
                city = city
            )
        )

}