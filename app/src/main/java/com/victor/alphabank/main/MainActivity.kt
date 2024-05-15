package com.victor.alphabank.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.victor.alphabank.core.ui.theme.AlphaBankTheme
import com.victor.alphabank.feature.loan.navigation.LoanRequestNavHost
import com.victor.alphabank.feature.loan.navigation.loanRequestNavGraph
import com.victor.alphabank.feature.loan.navigation.navigateToApprovedScreen
import com.victor.alphabank.feature.loan.navigation.navigateToDeniedScreen
import com.victor.alphabank.main.uiState.MainUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.getLocalLoanResult()

        setContent {
            AlphaBankTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val uiState = viewModel.uiState.collectAsState()

                    AlphaBankNavHost(navController, innerPadding)

                    when (uiState.value) {
                        is MainUiState.LoanResultSuccess -> {
                            if ((uiState.value as MainUiState.LoanResultSuccess).isLoanApproved) {
                                navController.navigateToApprovedScreen(
                                    (uiState.value as MainUiState.LoanResultSuccess).maxAmount ?: 0F
                                )
                            } else {
                                navController.navigateToDeniedScreen()
                            }
                        }

                        MainUiState.Empty,
                        MainUiState.Error -> {

                        }
                        MainUiState.Loading -> {

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AlphaBankNavHost(
        navController: NavHostController,
        innerPadding: PaddingValues
    ) {
        NavHost(
            navController = navController,
            startDestination = LoanRequestNavHost.Form.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            loanRequestNavGraph(navController)
        }
    }
}




