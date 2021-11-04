package com.resurrection.imkb.data.remote

import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface HandshakeApiService {
    @POST("api/handshake/start")
    @Headers("Content-Type: application/json")
    suspend fun requestHandshake(@Body request: HandshakeRequest): Response<HandshakeResponse>
}