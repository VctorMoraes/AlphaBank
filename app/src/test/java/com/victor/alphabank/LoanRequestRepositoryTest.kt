package com.victor.alphabank

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.victor.alphabank.data.database.dao.LoanResultDao
import com.victor.alphabank.data.loanRequest.datasource.remote.api.LoanRequestApi
import com.victor.alphabank.data.loanRequest.model.LoanResultModel
import com.victor.alphabank.data.loanRequest.model.UserModel
import com.victor.alphabank.data.loanRequest.repository.LoanRequestRepository
import com.victor.alphabank.data.loanRequest.repository.LoanRequestRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class LoanRequestRepositoryTest {

    private val mockWebServer: MockWebServer by lazy { MockWebServer() }

    private val loanResultDao: LoanResultDao = mockk(relaxed = true)

    private lateinit var repository: LoanRequestRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: LoanRequestApi

    @Before
    fun setup() {
        mockWebServer.start()

        val json = Json {
            ignoreUnknownKeys
        }
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient())
            .build()
            .create(LoanRequestApi::class.java)

        repository = LoanRequestRepositoryImpl(apiService, loanResultDao)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `request loan should return status denied`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"status\": \"DENIED\"}")
        mockWebServer.enqueue(response)

        val expectedResultModel = LoanResultModel("DENIED", null)

        repository.requestLoan(USER_MODEL).collectLatest { actualResultModel ->
            Assert.assertEquals(expectedResultModel, actualResultModel)
        }
    }

    @Test
    fun `request loan should return status approved`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"status\": \"APPROVED\", \"max_amount\": 150000 }")
        mockWebServer.enqueue(response)

        val expectedResultModel = LoanResultModel("APPROVED", 150000F)

        repository.requestLoan(USER_MODEL).collectLatest { actualResultModel ->
            Assert.assertEquals(expectedResultModel, actualResultModel)
        }
    }

    companion object {
        val USER_MODEL = UserModel(
            name = "Mithrandir",
            age = 24000,
            monthIncome = 1000F,
            city = "Minas Tirith"
        )
    }
}