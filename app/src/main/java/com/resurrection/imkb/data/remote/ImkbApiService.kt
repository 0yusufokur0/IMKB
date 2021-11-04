package com.resurrection.imkb.data.remote

import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.ListRequest
import com.resurrection.imkb.data.model.imkb.ListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ImkbApiService {

    @POST("api/stocks/list")
    @Headers("Content-Type: application/json")
    suspend fun requestList(
        @Header("X-VP-Authorization") XVPAuthorization: String,
        @Body request: ListRequest
    ): Response<ListResponse>

    @POST("api/stocks/detail")
    @Headers("Content-Type: application/json")
    suspend fun requestDetail(
        @Header("X-VP-Authorization") XVPAuthorization: String,
        @Body request: DetailRequest
    ): Response<DetailResponse>
}
