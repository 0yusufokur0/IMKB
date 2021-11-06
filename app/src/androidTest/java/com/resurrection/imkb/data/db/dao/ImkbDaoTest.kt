package com.resurrection.imkb.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.resurrection.imkb.data.db.ImkbDatabase
import com.resurrection.imkb.data.model.imkb.Stock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ImkbDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ImkbDatabase
    private lateinit var imkbDao: ImkbDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ImkbDatabase::class.java
        ).allowMainThreadQueries().build()

        imkbDao = database.imkbDao()

    }


    @Test
    fun insertStockTest() = runBlocking{
        var stock = Stock(1,1.0,1.0,false,false,1.0,1.0,"symbol",1.0)
            imkbDao.insertStock(stock)
        val value = imkbDao.getStocks()
        assertThat(value).contains(stock)
    }

    @Test
    fun removeStockTest() = runBlocking{
        var stock = Stock(1,1.0,1.0,false,false,1.0,1.0,"symbol",1.0)
        imkbDao.insertStock(stock)
        imkbDao.deleteStock(stock)
        val value = imkbDao.getStocks()
        assertThat(value).doesNotContain(stock)
    }

    @Test
    fun getStockByIdTest() = runBlocking{
        var stockId = 1
        var stock = Stock(1,stockId.toDouble(),1.0,false,false,1.0,1.0,"symbol",1.0)
        imkbDao.insertStock(stock)
        val value = imkbDao.getStock(stockId.toDouble())
        assertThat(value).isEqualTo(stock)
    }

    @After
    fun tearDown() {
        database.close()
    }

}