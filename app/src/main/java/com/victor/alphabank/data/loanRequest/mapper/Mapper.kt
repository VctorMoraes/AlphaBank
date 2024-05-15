package com.victor.alphabank.data.loanRequest.mapper

import com.victor.alphabank.data.database.entities.LoanResultEntity
import com.victor.alphabank.data.loanRequest.datasource.remote.dto.LoanRequestDTO
import com.victor.alphabank.data.loanRequest.datasource.remote.dto.UserDTO
import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.data.loanRequest.model.UserModel

fun LoanRequestDTO.toModel() =
    LoanResultModel(
        status = this.status,
        maxAmount = this.maxAmount
    )

fun LoanResultEntity.toModel() =
    LoanResultModel(
        status = this.status,
        maxAmount = this.maxAmount
    )

fun LoanRequestDTO.toEntity() =
    LoanResultEntity(
        status = this.status,
        maxAmount = this.maxAmount
    )

fun UserDTO.toModel() = UserModel(
    name = this.name,
    age = this.age,
    monthIncome = this.monthIncome,
    city = this.city
)

fun UserModel.toDTO() = UserDTO(
    name = this.name,
    age = this.age,
    monthIncome = this.monthIncome,
    city = this.city
)