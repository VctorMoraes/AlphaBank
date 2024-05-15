package com.victor.alphabank.feature.loan.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.victor.alphabank.feature.loan.uiState.LoanRequestResultUiState
import com.victor.alphabank.feature.loan.viewmodel.LoanRequestViewModel

@Composable
fun LoanRequestScreen(
    onLoanApproved: (maxAmount: Float) -> Unit,
    onLoanDenied: () -> Unit,
    viewModel: LoanRequestViewModel = hiltViewModel()
) {
    var showLoadingIndicator by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .gesturesDisabled(showLoadingIndicator),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState = viewModel.loanResultUiState.collectAsState()

        LoanRequestForm(showLoadingIndicator = showLoadingIndicator)

        when (uiState.value) {
            is LoanRequestResultUiState.Success -> {
                showLoadingIndicator = false
                with((uiState.value as LoanRequestResultUiState.Success)) {
                    if (isLoanApproved) {
                        onLoanApproved(
                            (uiState.value as LoanRequestResultUiState.Success).maxAmount ?: 0F
                        )
                    } else {
                        onLoanDenied()
                    }
                }
            }

            LoanRequestResultUiState.Error -> {
                val context = LocalContext.current
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            LoanRequestResultUiState.Loading -> {
                showLoadingIndicator = true
            }

            LoanRequestResultUiState.Empty -> {

            }
        }
    }
}

@Composable
fun LoanRequestForm(
    viewModel: LoanRequestViewModel = hiltViewModel(),
    showLoadingIndicator: Boolean
) {
    val focusManager = LocalFocusManager.current
    var showAllErrors by rememberSaveable { mutableStateOf(false) }

    LoanRequestTextField(
        label = "Nome",
        value = viewModel.userInput.name,
        onValueChange = {
            viewModel.updateName(it)
        },
        keyboardType = KeyboardType.Text,
        isError = showAllErrors && !viewModel.inputValidation.isNameValid,
        errorMessage = "Nome deve ter mais de 8 caracteres"
    )

    LoanRequestTextField(
        label = "Idade",
        value = viewModel.userInput.age,
        onValueChange = {
            viewModel.updateAge(it)
        },
        keyboardType = KeyboardType.NumberPassword,
        isError = showAllErrors && !viewModel.inputValidation.isAgeValid,
        errorMessage = "Você deve ter entre 18 e 65 anos"
    )

    LoanRequestTextField(
        label = "Renda Mensal",
        value = viewModel.userInput.monthIncome,
        onValueChange = {
            viewModel.updateMonthIncome(it)
        },
        keyboardType = KeyboardType.Decimal,
        isError = showAllErrors && !viewModel.inputValidation.isMonthIncomeValid,
        errorMessage = "Renda mensal deve ser superior a R$0,00"
    )

    LoanRequestTextField(
        label = "Cidade",
        value = viewModel.userInput.city,
        onValueChange = {
            viewModel.updateCity(it)
        },
        keyboardType = KeyboardType.Text,
        isError = showAllErrors && !viewModel.inputValidation.isCityValid,
        errorMessage = "Cidade não pode ficar vazia"
    )

    Button(
        modifier = Modifier
            .then(
                if (viewModel.inputValidation.isRequestLoanButtonEnabled) {
                    Modifier
                } else {
                    Modifier.alpha(0.5f)
                }
            )
            .height(OutlinedTextFieldDefaults.MinHeight)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onClick = {
            if (viewModel.inputValidation.isRequestLoanButtonEnabled) {
                focusManager.clearFocus()
                viewModel.requestLoan()
            } else {
                showAllErrors = true
            }
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        if (showLoadingIndicator) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                strokeWidth = 2.dp,
                color = Color.White,
                trackColor = Color.Transparent,
            )
        } else {
            Text(
                text = "Solicitar empréstimo",
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        }

    }
}

@Composable
fun LoanRequestTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    isError: Boolean,
    errorMessage: String
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        label = { Text(label) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isError) {
                Icon(Icons.Rounded.Warning, "error", tint = MaterialTheme.colorScheme.error)
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        )
    )
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }