package com.victor.alphabank.domain

import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.data.loanRequest.repository.LoanRequestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalLoanResultUseCaseImpl @Inject constructor(
    private val loanRequestRepository: LoanRequestRepository
) : GetLocalLoanResultUseCase {
    override fun invoke(): Flow<LoanResultModel> =
        loanRequestRepository.getLocalLoanResult()
}