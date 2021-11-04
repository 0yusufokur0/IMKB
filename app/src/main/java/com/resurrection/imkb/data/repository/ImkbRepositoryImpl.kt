package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.db.dao.ImkbDao
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.ListRequest
import com.resurrection.imkb.data.model.imkb.ListResponse
import com.resurrection.imkb.data.remote.HandshakeApiService
import com.resurrection.imkb.data.remote.ImkbApiService
import com.resurrection.imkb.util.Resource
import com.resurrection.imkb.util.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImkbRepositoryImpl @Inject constructor(
    private val imkbApiService: ImkbApiService,
    private val handshakeApiService: HandshakeApiService,
    private val imkbDao: ImkbDao,
) : ImkbRepository {

    override suspend fun getHandShake(request: HandshakeRequest)
            : Flow<Resource<HandshakeResponse>> = flow {
        emit(getResourceByNetworkRequest { handshakeApiService.requestHandshake(request) })
    }

    override suspend fun getRequestList(XVPAuthorization: String, request: ListRequest)
            : Flow<Resource<ListResponse>> = flow {
        emit(getResourceByNetworkRequest { imkbApiService.requestList(XVPAuthorization, request) })
    }


    override suspend fun getRequestDetail(authStr: String, detailRequest: DetailRequest)
            : Flow<Resource<DetailResponse>> = flow {
        emit(getResourceByNetworkRequest { imkbApiService.requestDetail(authStr, detailRequest) })
    }


}












