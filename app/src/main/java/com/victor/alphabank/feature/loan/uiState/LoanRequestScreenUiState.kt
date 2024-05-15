package com.victor.alphabank.feature.loan.uiState

sealed interface LoanRequestScreenUiState {

    data class UserInput(
        val name: String = "",
        val age: String = "",
        val monthIncome: String = "",
        val city: String = ""
    )

    data class InputValidation(
        val isNameValid: Boolean = false,
        val isAgeValid: Boolean = false,
        val isMonthIncomeValid: Boolean = false,
        val isCityValid: Boolean = false,
    ) {
        val isRequestLoanButtonEnabled: Boolean
            get() = isNameValid && isAgeValid && isMonthIncomeValid && isCityValid
    }
}