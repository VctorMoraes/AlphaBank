package com.victor.alphabank.data.loanRequest.repository

import android.util.Log
import com.victor.alphabank.core.NoConnectionException
import com.victor.alphabank.data.database.dao.LoanResultDao
import com.victor.alphabank.data.loanRequest.datasource.remote.api.LoanRequestApi
import com.victor.alphabank.data.loanRequest.mapper.toDTO
import com.victor.alphabank.data.loanRequest.mapper.toEntity
import com.victor.alphabank.data.loanRequest.mapper.toModel
import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.data.loanRequest.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class LoanRequestRepositoryImpl @Inject constructor(
    private val loanRequestApi: LoanRequestApi,
    private val loanResultDao: LoanResultDao
) : LoanRequestRepository {

    override fun requestLoan(
        user: UserModel
    ) = flow {
        try {
            loanRequestApi.requestLoan(user.toDTO()).fold({ dto ->
                dto?.let {
                    loanResultDao.insertAll(it.toEntity())
                    emit(it.toModel())
                }
            }, {
                Log.e("REQUEST ERROR", it?.string() ?: "")
            })
        } catch (e: NoConnectionException) {
            Log.e("NETWORK ERROR", e.stackTraceToString())
        }
    }

    override fun getLocalLoanResult(): Flow<LoanResultModel> = flow {
        loanResultDao.getAll().map {
            emit(it.toModel())
        }
    }

    @OptIn(ExperimentalContracts::class)
    suspend fun <R, T> Response<T>.fold(
        onSuccess: suspend (value: T?) -> R,
        onFailure: (error: ResponseBody?) -> R
    ): R {
        contract {
            callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
            callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
        }

        return if (this.isSuccessful) {
            onSuccess(this.body())
        } else {
            onFailure(this.errorBody())
        }
    }
}

