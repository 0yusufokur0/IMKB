package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.*
import com.resurrection.imkb.ui.base.data.Resource
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

    suspend fun insertDetailResponse(stock: Stock): Flow<Resource<Unit>>

    suspend fun removeDetailResponse(stock: Stock): Flow<Resource<Unit>>

    suspend fun getStock(id: Double): Flow<Resource<Stock>>


    suspend fun getStocks():Flow<Resource<List<Stock>>>

}

