package com.victor.alphabank.data.loanRequest.datasource.remote.api

import com.victor.alphabank.data.loanRequest.datasource.remote.dto.LoanRequestDTO
import com.victor.alphabank.data.loanRequest.datasource.remote.dto.UserDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoanRequestApi {

    @POST("/")
    suspend fun requestLoan(
        @Body user: UserDTO
    ): Response<LoanRequestDTO>

}