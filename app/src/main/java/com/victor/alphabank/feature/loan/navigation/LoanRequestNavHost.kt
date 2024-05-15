package com.victor.alphabank.feature.loan.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.victor.alphabank.feature.loan.ui.LoanApprovedScreen
import com.victor.alphabank.feature.loan.ui.LoanDeniedScreen
import com.victor.alphabank.feature.loan.ui.LoanRequestScreen

fun NavController.navigateToApprovedScreen(maxAmount: Float, navOptions: NavOptions? = null) =
    navigate("${LoanRequestNavHost.Approved.name}/${maxAmount}", navOptions)

fun NavController.navigateToDeniedScreen(navOptions: NavOptions? = null) =
    navigate(LoanRequestNavHost.Denied.name, navOptions)

fun NavGraphBuilder.loanRequestNavGraph(
    navController: NavController
) {
    composable(route = LoanRequestNavHost.Form.name) {
        LoanRequestScreen(
            onLoanApproved = {
                navController.navigateToApprovedScreen(it)
            },
            onLoanDenied = {
                navController.navigateToDeniedScreen()
            }
        )
    }
    composable(
        route = "${LoanRequestNavHost.Approved.name}/{maxAmount}",
        arguments = listOf(
            navArgument(name = "maxAmount") { NavType.StringType },
        )
    ) {
        LoanApprovedScreen(it.arguments?.getString("maxAmount")?.toFloat() ?: 0F)
    }
    composable(route = LoanRequestNavHost.Denied.name) {
        LoanDeniedScreen()
    }
}

enum class LoanRequestNavHost {
    Form,
    Approved,
    Denied
}