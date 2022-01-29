package com.techand.sampletest

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.techand.sampletest.data.models.User
import com.techand.sampletest.data.network.ApiService
import com.techand.sampletest.data.repository.UserInfoRepository
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class UserInfoRepositoryTest : BaseUnitTest() {

    private var apiService: ApiService = mock()
    private var repository = UserInfoRepository(apiService)
    private val playlist = mock<List<User>>()
    private val expectedResults = Result.success(playlist)
    private val exception = RuntimeException("Error")


    @Test
    fun getDataFromApiService() = runBlockingTest {
        repository.getUser()
        verify(apiService, times(1)).fetchUsers()
    }

    @Test
    fun emitUserFromApiService() = runBlockingTest {
        whenever(apiService.fetchUsers()).thenReturn(flow {
            emit(Result.success(playlist))
        })

        assertEquals(expectedResults, repository.getUser().first())
    }
    @Test
    fun emitErrorFromApiService() = runBlockingTest {
        whenever(apiService.fetchUsers()).thenReturn(flow {
            emit(Result.failure<List<User>>(exception))
        })

        assertEquals(exception, repository.getUser().first().exceptionOrNull())
    }
}