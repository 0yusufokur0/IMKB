package com.resurrection.imkb.data.db.dao

import androidx.room.*
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.Stock

@Dao
interface ImkbDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailResponse(detailResponse: DetailResponse)

    @Delete
    suspend fun deleteStock(detailResponse: DetailResponse)

    @Query("SELECT * FROM detail_response")
    suspend fun getStockFromDataBase():List<DetailResponse>

    @Query("SELECT * FROM detail_response WHERE bid=:id ")
    suspend fun getDetailResponse(id:Double):DetailResponse

    @Query("SELECT EXISTS (SELECT * FROM detail_response WHERE bid = :id) LIMIT 1")
    fun detailResponseExists(id: Double): Boolean
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
