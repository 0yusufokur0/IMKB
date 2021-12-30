package com.resurrection.imkb.ui.main.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.data.repository.FakeImkbRepository
import com.resurrection.imkb.ui.base.data.Status
import com.resurrection.movies.MainCoroutineRule
import com.resurrection.movies.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DetailViewModel
    @Before
    fun setUp() {
        viewModel = DetailViewModel(FakeImkbRepository())
    }

    @Test
    fun `get request detail with non null parameter return success`(){
        viewModel.getDetail("XVPAuthorization", DetailRequest("id"))
        val value = viewModel.detail.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get request detail without auth, detail request return error`(){
        viewModel.getDetail("", DetailRequest(""))
        val value = viewModel.detail.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert stock with stock return success`(){
        var stock = Stock(1,1.0,1.0,false,false,1.0,1.0,"symbol",1.0)
        viewModel.insertFavorite(stock)
        val value = viewModel.isAdded.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert stock without stock bid return success`(){
        var stock = Stock(1,0.0,1.0,false,false,1.0,1.0,"symbol",1.0)
        viewModel.insertFavorite(stock)
        val value = viewModel.isAdded.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `remove stock with stock return success`(){
        var stock = Stock(1,1.0,1.0,false,false,1.0,1.0,"symbol",1.0)
        viewModel.deleteFavorite(stock)
        val value = viewModel.isDeleted.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `remove stock with out stock return error`(){
        var stock = Stock(1,0.0,1.0,false,false,1.0,1.0,"symbol",1.0)
        viewModel.deleteFavorite(stock)
        val value = viewModel.isDeleted.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `get favorite state with id return success`(){
        viewModel.getFavoriteState(1.0)
        val value = viewModel.isFavorite.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `get favorite state without id return success`(){
        viewModel.getFavoriteState(0.0)
        val value = viewModel.isFavorite.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}