package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.model.ServiceError
import com.resurrection.imkb.data.model.ServiceStatus
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.*
import com.resurrection.imkb.util.Resource
import com.resurrection.imkb.util.getResourceByDatabaseRequest
import com.resurrection.imkb.util.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeImkbRepository:ImkbRepository {

    var serviceStatus =ServiceStatus(ServiceError(0,""),true)

    var handshakeResponse = HandshakeResponse("","","","",serviceStatus)

    var listResponse = ListResponse(serviceStatus, listOf<Stock>())

    override suspend fun getHandShake(request: HandshakeRequest): Flow<Resource<HandshakeResponse>> = flow {
        emit(getResourceByNetworkRequest { getHandShakeTest() })
    }

    override suspend fun getRequestList(
        XVPAuthorization: String,
        request: ListRequest
    ): Flow<Resource<ListResponse>> = flow {
        emit(getResourceByNetworkRequest { getRequestListTest() })
    }

    override suspend fun getRequestDetail(
        authStr: String,
        detailRequest: DetailRequest
    ): Flow<Resource<DetailResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertDetailResponse(stock: Stock): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeDetailResponse(stock: Stock): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStock(id: Double): Flow<Resource<Stock>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStocks(): Flow<Resource<List<Stock>>> {
        TODO("Not yet implemented")
    }



    private suspend fun getHandShakeTest(): Response<HandshakeResponse> {

        var temp: Response<HandshakeResponse> = Response.success(handshakeResponse)
        return temp
    }

    private suspend fun getRequestListTest() :Response<ListResponse>{
        var temp : Response<ListResponse> = Response.success(listResponse)
        return temp
    }
}