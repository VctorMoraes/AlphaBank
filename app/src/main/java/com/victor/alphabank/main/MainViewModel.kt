package com.victor.alphabank.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.alphabank.domain.GetLocalLoanResultUseCase
import com.victor.alphabank.main.uiState.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalLoanResultUseCase: GetLocalLoanResultUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState.Empty)
    val uiState: StateFlow<MainUiState>
        get() = _uiState.asStateFlow()

    fun getLocalLoanResult() {
        viewModelScope.launch {
            getLocalLoanResultUseCase().collectLatest { loanResult ->
                _uiState.update {
                    MainUiState.LoanResultSuccess(
                        isLoanApproved(loanResult.status), loanResult.maxAmount
                    )
                }
            }
        }
    }

    private fun isLoanApproved(status: String): Boolean = status == "APPROVED"
}
