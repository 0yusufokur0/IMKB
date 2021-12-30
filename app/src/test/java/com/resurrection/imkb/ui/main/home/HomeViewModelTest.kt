package com.resurrection.imkb.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.imkb.ListRequest
import com.resurrection.imkb.data.repository.FakeImkbRepository
import com.resurrection.imkb.ui.base.data.Status
import com.resurrection.movies.MainCoroutineRule
import com.resurrection.movies.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(FakeImkbRepository())
    }

    @Test
    fun `get handshake with handshake request attributes return success`(){
        viewModel.getAuth(HandshakeRequest("id", "model", "manufacturer", "platformName", "systemVersion"))
        val value = viewModel.auth.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get handshake without handshake request attributes return error`(){
        viewModel.getAuth(HandshakeRequest("","","","",""))
        val value = viewModel.auth.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `get request list with XVPAuthorization, list request  return success`(){
        viewModel.getResponseList("XVPAuthorization", ListRequest("period"))
        val  value = viewModel.listResponse.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get request list with out XVPAuthorization, list request period return error`(){
        viewModel.getResponseList("", ListRequest(""))
        val  value = viewModel.listResponse.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}