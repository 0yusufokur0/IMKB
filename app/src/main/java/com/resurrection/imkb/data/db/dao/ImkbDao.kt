package com.resurrection.imkb.data.db.dao

import androidx.room.*
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.Stock

@Dao
interface ImkbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: Stock)

    @Delete
    suspend fun deleteStock(stock: Stock)
    @Query("SELECT * FROM stock")
    suspend fun getStocks(): List<Stock>

    @Query("SELECT * FROM stock WHERE bid=:id ")
    suspend fun getStock(id: Double): Stock
/*
    @Query("SELECT EXISTS (SELECT * FROM stock WHERE bid = :id) LIMIT 1")
    fun detailResponseExists(id: Double): Boolean*/
}


