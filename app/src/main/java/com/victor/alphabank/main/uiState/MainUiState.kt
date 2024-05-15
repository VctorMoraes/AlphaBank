package com.victor.alphabank.main.uiState

import com.victor.alphabank.feature.loan.uiState.LoanRequestResultUiState

sealed interface MainUiState {

    data class LoanResultSuccess(
        val isLoanApproved: Boolean = false,
        val maxAmount: Float? = null,
    ) : MainUiState

    data object Error: MainUiState

    data object Loading: MainUiState

    data object Empty: MainUiState
}