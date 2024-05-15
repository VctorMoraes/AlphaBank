package com.victor.alphabank.data.loanRequest.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoanRequestDTO(
    @SerialName("status") var status: String,
    @SerialName("max_amount") var maxAmount: Float? = null,
)