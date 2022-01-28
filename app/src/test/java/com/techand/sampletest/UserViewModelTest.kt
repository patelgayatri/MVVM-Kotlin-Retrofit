package com.techand.sampletest

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.techand.sampletest.data.models.User
import com.techand.sampletest.data.repository.UserInfoRepository
import com.techand.sampletest.ui.UserViewModel
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.utils.getValueForTest

@ExperimentalCoroutinesApi
class UserViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: UserViewModel
    private var repository: UserInfoRepository = mock()
    private var users = mock<List<User>>()
    private var expectedUsers = Result.success(users)


    @Before
    fun setUp(){
        viewModel = UserViewModel(repository)
    }

    @Test
    fun getDataFromRepository() = runBlockingTest {
        whenever(repository.getUser()).thenReturn(flow {
            emit(expectedUsers)
        })
        viewModel.getUser().getValueForTest()
        verify(repository, times(1)).getUser()
    }
    @Test
    fun emitUsersFromRepository() = runBlockingTest {
        whenever(repository.getUser()).thenReturn(flow {
            emit(expectedUsers)
        })
        assertEquals(expectedUsers,viewModel.getUser().getValueForTest())
    }

}