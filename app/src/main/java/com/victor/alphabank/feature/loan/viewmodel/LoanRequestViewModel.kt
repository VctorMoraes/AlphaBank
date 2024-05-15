package com.victor.alphabank.feature.loan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.alphabank.domain.LoanRequestUseCase
import com.victor.alphabank.feature.loan.uiState.LoanRequestResultUiState
import com.victor.alphabank.feature.loan.uiState.LoanRequestScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanRequestViewModel @Inject constructor(
    private val loanRequestUseCase: LoanRequestUseCase
) : ViewModel() {

    private val _loanResultUiState: MutableStateFlow<LoanRequestResultUiState> =
        MutableStateFlow(LoanRequestResultUiState.Empty)
    val loanResultUiState: StateFlow<LoanRequestResultUiState>
        get() = _loanResultUiState.asStateFlow()

    var userInput by mutableStateOf(LoanRequestScreenUiState.UserInput())
        private set

    var inputValidation by mutableStateOf(LoanRequestScreenUiState.InputValidation())
        private set

    fun requestLoan() {
        viewModelScope.launch {
            _loanResultUiState.update {
                LoanRequestResultUiState.Loading
            }

            loanRequestUseCase(
                userInput.name,
                userInput.age.toInt(),
                userInput.monthIncome.toFloat(),
                userInput.city
            )
                .collectLatest { response ->
                    _loanResultUiState.update { uiState ->
                        uiState.updateResultUiStateSuccess(isLoanApproved(response.status), response.maxAmount)
                    }
                }
        }
    }

    fun updateName(name: String) {
        userInput = userInput.copy(
            name = name
        )
        validateName()
    }

    private fun validateName() {
        inputValidation = inputValidation.copy(
            isNameValid = userInput.name.length > NAME_MIN_LENGTH
        )
    }

    fun updateAge(age: String) {
        userInput = userInput.copy(
            age = age
        )
        validateAge()
    }

    private fun validateAge() {
        inputValidation = inputValidation.copy(
            isAgeValid = try {
                userInput.age.toInt() in 18..65
            } catch (e: NumberFormatException) {
                false
            }
        )
    }

    fun updateMonthIncome(monthIncome: String) {
        userInput = userInput.copy(
            monthIncome = monthIncome
        )
        validateMonthIncome()
    }

    private fun validateMonthIncome() {
        inputValidation = inputValidation.copy(
            isMonthIncomeValid = try {
                userInput.monthIncome.toFloat() > 0F
            } catch (e: NumberFormatException) {
                false
            }

        )
    }

    fun updateCity(city: String) {
        userInput = userInput.copy(
            city = city
        )
        validateCity()
    }

    private fun validateCity() {
        inputValidation = inputValidation.copy(
            isCityValid = userInput.city.isNotEmpty()
        )
    }

    private fun LoanRequestResultUiState.updateResultUiStateSuccess(
        status: Boolean,
        maxAmount: Float? = null
    ): LoanRequestResultUiState.Success {
        return when (this) {
            is LoanRequestResultUiState.Success -> {
                this.copy(status, maxAmount)
            }

            else -> {
                LoanRequestResultUiState.Success(status, maxAmount)
            }

        }
    }


    private fun isLoanApproved(status: String): Boolean = status == "APPROVED"

    companion object {
        const val NAME_MIN_LENGTH = 8
    }
}