package com.victor.alphabank.feature.loan.uiState

sealed interface LoanRequestResultUiState {

    data class Success(
        val isLoanApproved: Boolean = false,
        val maxAmount: Float? = null,
    ) : LoanRequestResultUiState

    data object Error : LoanRequestResultUiState

    data object Loading : LoanRequestResultUiState

    data object Empty : LoanRequestResultUiState
}