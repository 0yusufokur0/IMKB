package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.model.ServiceError
import com.resurrection.imkb.data.model.ServiceStatus
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.*
import com.resurrection.imkb.ui.base.data.Resource
import com.resurrection.imkb.ui.base.data.getResourceByDatabaseRequest
import com.resurrection.imkb.ui.base.data.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeImkbRepository : ImkbRepository {

    var serviceStatus = ServiceStatus(ServiceError(0, ""), true)

    var handshakeResponse = HandshakeResponse("", "", "", "", serviceStatus)

    var listResponse = ListResponse(serviceStatus, listOf<Stock>())
    var detailResponse = DetailResponse(0.0, 0.0, 0, 0.0, listOf<Graphic>(),
        0.0, false, false, 0.0, 0.0, 0.0, 0.0,0.0,
            serviceStatus,"",0.0)
    var stock = Stock(1,1.0,1.0,false,false,1.0,1.0,"symbol",1.0)

    override suspend fun getHandShake(request: HandshakeRequest): Flow<Resource<HandshakeResponse>> =
        flow {
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
    ): Flow<Resource<DetailResponse>> = flow {
        emit(getResourceByNetworkRequest { getRequestDetailTest()})
    }

    override suspend fun insertDetailResponse(stock: Stock): Flow<Resource<Unit>> = flow{
        emit(getResourceByDatabaseRequest { insertDetailResponseTest() })
    }

    override suspend fun removeDetailResponse(stock: Stock): Flow<Resource<Unit>> = flow{
        emit(getResourceByDatabaseRequest { removeDetailResponseTest() })
    }


    override suspend fun getStock(id: Double): Flow<Resource<Stock>> = flow{
        emit(getResourceByNetworkRequest { getStockTest() })
    }

    override suspend fun getStocks(): Flow<Resource<List<Stock>>> = flow{
        emit(getResourceByNetworkRequest { getStocksTest() })
    }


    private suspend fun getHandShakeTest(): Response<HandshakeResponse> =Response.success(handshakeResponse)
    private suspend fun getRequestListTest(): Response<ListResponse> = Response.success(listResponse)
    private suspend fun getRequestDetailTest() :Response<DetailResponse> = Response.success(detailResponse)
    private suspend fun insertDetailResponseTest() = Unit
    private suspend fun removeDetailResponseTest() = Unit
    private suspend fun getStockTest() = Response.success(stock)
    private suspend fun getStocksTest() = Response.success(listOf<Stock>(stock))
}