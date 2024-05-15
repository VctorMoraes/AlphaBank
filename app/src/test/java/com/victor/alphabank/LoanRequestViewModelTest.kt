package com.victor.alphabank

import com.victor.alphabank.feature.loan.uiState.LoanRequestResultUiState
import com.victor.alphabank.feature.loan.viewmodel.LoanRequestViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class LoanRequestViewModelTest {
    private val useCase = FakeLoanRequestUseCase()
    private val viewModel = LoanRequestViewModel(useCase)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `userInput field are initiated as empty`() {
        with(viewModel.userInput) {
            Assert.assertEquals("", name)
            Assert.assertEquals("", age)
            Assert.assertEquals("", monthIncome)
            Assert.assertEquals("", city)
        }
    }

    @Test
    fun `userInput name is updated when update name is called`() {
        Assert.assertEquals("", viewModel.userInput.name)

        viewModel.updateName("Victor")

        Assert.assertEquals("Victor", viewModel.userInput.name)
    }

    @Test
    fun `userInput age is updated when update age is called`() {
        Assert.assertEquals("", viewModel.userInput.age)

        viewModel.updateAge("22")

        Assert.assertEquals("22", viewModel.userInput.age)
    }

    @Test
    fun `userInput monthIncome is updated when update monthIncome is called`() {
        Assert.assertEquals("", viewModel.userInput.monthIncome)

        viewModel.updateMonthIncome("5000")

        Assert.assertEquals("5000", viewModel.userInput.monthIncome)
    }

    @Test
    fun `userInput city is updated when update city is called`() {
        Assert.assertEquals("", viewModel.userInput.city)

        viewModel.updateCity("Campinas")

        Assert.assertEquals("Campinas", viewModel.userInput.city)
    }

    @Test
    fun `name is valid when update name is called with name that has more than 8 characters`() {
        viewModel.updateName("Victor Moraes")

        Assert.assertEquals(true, viewModel.inputValidation.isNameValid)
    }

    @Test
    fun `name is invalid when update name is called with name that has less than 8 characters`() {
        viewModel.updateName("Victor")

        Assert.assertEquals(false, viewModel.inputValidation.isNameValid)
    }

    @Test
    fun `age is valid when update age is called with age in between 18 and 65`() {
        viewModel.updateAge("22")

        Assert.assertEquals(true, viewModel.inputValidation.isAgeValid)
    }

    @Test
    fun `age is invalid when update age is called with age minor than 18`() {
        viewModel.updateAge("17")

        Assert.assertEquals(false, viewModel.inputValidation.isAgeValid)
    }

    @Test
    fun `age is invalid when update age is called with age minor than 65`() {
        viewModel.updateAge("66")

        Assert.assertEquals(false, viewModel.inputValidation.isAgeValid)
    }

    @Test
    fun `monthIncome is valid when update monthIncome is called with monthIncome higher than 0`() {
        viewModel.updateMonthIncome("1")

        Assert.assertEquals(true, viewModel.inputValidation.isMonthIncomeValid)
    }

    @Test
    fun `monthIncome is invalid when update monthIncome is called with monthIncome equals 0`() {
        viewModel.updateMonthIncome("0")

        Assert.assertEquals(false, viewModel.inputValidation.isMonthIncomeValid)
    }

    @Test
    fun `city is valid when update city is called with city that is not empty`() {
        viewModel.updateCity("Campinas")

        Assert.assertEquals(true, viewModel.inputValidation.isCityValid)
    }

    @Test
    fun `city is invalid when update city is called with empty city`() {
        viewModel.updateCity("")

        Assert.assertEquals(false, viewModel.inputValidation.isCityValid)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `result ui state is updated when requestLoan is called`() =
        runTest(UnconfinedTestDispatcher()) {
            Assert.assertEquals(LoanRequestResultUiState.Empty, viewModel.loanResultUiState.value)

            with(viewModel) {
                updateName("Victor")
                updateAge("22")
                updateMonthIncome("2500")
                updateCity("Campinas")
            }

            viewModel.requestLoan()

            Assert.assertEquals(
                LoanRequestResultUiState.Success(
                    true, 2500F
                ), viewModel.loanResultUiState.value
            )
        }
}