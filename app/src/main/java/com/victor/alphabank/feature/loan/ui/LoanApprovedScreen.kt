package com.victor.alphabank.feature.loan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.alphabank.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LoanApprovedScreen(maxAmount: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Rounded.Done,
            contentDescription = "success",
            tint = Color.Green
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.loan_approved_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        val approvedValue: String =
            NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(maxAmount)

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.approved_value, approvedValue),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
@Preview()
fun LoanApprovedScreenPreview() {
    LoanApprovedScreen(200f)
}