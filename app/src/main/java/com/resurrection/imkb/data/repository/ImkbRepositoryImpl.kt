package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.db.dao.ImkbDao
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.*
import com.resurrection.imkb.data.remote.HandshakeApiService
import com.resurrection.imkb.data.remote.ImkbApiService
import com.resurrection.imkb.util.data.Resource
import com.resurrection.imkb.util.data.getResourceByDatabaseRequest
import com.resurrection.imkb.util.data.getResourceByNetworkRequest
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

    override suspend fun insertDetailResponse(stock: Stock): Flow<Resource<Unit>> =
        flow {
            emit(getResourceByDatabaseRequest { imkbDao.insertStock(stock) })
        }

    override suspend fun removeDetailResponse(stock: Stock): Flow<Resource<Unit>> =
        flow {
            emit(getResourceByDatabaseRequest { imkbDao.deleteStock(stock) })
        }

    override suspend fun getStock(id: Double): Flow<Resource<Stock>> = flow {
        emit(getResourceByDatabaseRequest { imkbDao.getStock(id) })
    }

    override suspend fun getStocks(): Flow<Resource<List<Stock>>> = flow {
        emit(getResourceByDatabaseRequest { imkbDao.getStocks() })
    }

}












