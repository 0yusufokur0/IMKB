package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.*
import com.resurrection.imkb.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImkbRepository {

    // Network
    suspend fun getHandShake(request: HandshakeRequest): Flow<Resource<HandshakeResponse>>
    suspend fun getRequestList(
        XVPAuthorization: String,
        request: ListRequest
    ): Flow<Resource<ListResponse>>

    suspend fun getRequestDetail(
        authStr: String,
        detailRequest: DetailRequest
    ): Flow<Resource<DetailResponse>>

    suspend fun insertDetailResponse(detailResponse: DetailResponse):Flow<Resource<Unit>>

    suspend fun removeDetailResponse(detailResponse: DetailResponse):Flow<Resource<Unit>>

    suspend fun getDetailResponse(id:Double):Flow<Resource<DetailResponse>>

    suspend fun detailResponseExists(id:Double):Flow<Resource<Boolean>>


/*    // Network
    suspend fun getMovieById(id: String, page: Int): Flow<Resource<Any>>
    suspend fun getMovieDetail(imdbId: String): Flow<Resource<Any>>

    // Database
    suspend fun insertMovie(movie: Any): Flow<Resource<Unit>>
    suspend fun removeMovie(movie: Any): Flow<Resource<Unit>>
    suspend fun getFavoriteMovies(): Flow<Resource<List<Any>>>
    suspend fun getMovieById(imdbID: String): Flow<Resource<Any>>
    suspend fun getMovieByTitle(title: String): Flow<Resource<List<Any>>>*/
}

