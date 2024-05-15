package com.victor.alphabank.data.loanRequest.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id") var id: String? = null,
    @SerialName("name") val name: String,
    @SerialName("age") val age: Int,
    @SerialName("month_income") val monthIncome: Float,
    @SerialName("city") val city: String
)