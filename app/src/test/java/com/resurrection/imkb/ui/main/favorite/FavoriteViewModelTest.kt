package com.resurrection.imkb.ui.main.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.data.repository.FakeImkbRepository
import com.resurrection.imkb.util.data.Status
import com.resurrection.movies.MainCoroutineRule
import com.resurrection.movies.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    var stock = Stock(1,1.0,1.0,false,false,1.0,1.0,"symbol",1.0)

    private lateinit var viewModel: FavoriteViewModel
    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(FakeImkbRepository())
    }

    @Test
    fun `get stock return success`(){
        viewModel.getStocks()
        val value = viewModel.stocks.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

}