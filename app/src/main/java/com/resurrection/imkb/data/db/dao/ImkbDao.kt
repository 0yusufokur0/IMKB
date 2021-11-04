package com.resurrection.imkb.data.db.dao

import androidx.room.*
import com.resurrection.imkb.data.model.imkb.Stock

@Dao
interface ImkbDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: Stock)

    @Delete
    suspend fun deleteStock(stock: Stock)

    @Query("SELECT * FROM stock")
    suspend fun getStockFromDataBase():List<Stock>


}


/*
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertMovie(movie: SearchItem)

@Delete
suspend fun removeMovie(movie: SearchItem)

@Query("SELECT * FROM search_item")
suspend fun getFavoriteMovies(): List<SearchItem>

@Query("SELECT * FROM search_item where  imdbId like :imdbID")
suspend fun getMovieById(imdbID: String): SearchItem

@Query("SELECT * FROM search_item WHERE title LIKE '%' || :title || '%' OR imdbID LIKE '%' || :title || '%'")
suspend fun getMovieByTitle(title: String): List<SearchItem>*/
