package com.resurrection.imkb.data.repository

import com.resurrection.imkb.data.db.dao.ImkbDao
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.ListRequest
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.data.remote.HandshakeApiService
import com.resurrection.imkb.data.remote.ImkbApiService
import com.resurrection.imkb.ui.base.data.Resource
import com.resurrection.imkb.ui.base.data.getDataByDatabase
import com.resurrection.imkb.ui.base.data.getDataByNetwork
import com.resurrection.imkb.ui.base.data.getResourceByDatabaseRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImkbRepositoryImpl @Inject constructor(
    private val imkbApiService: ImkbApiService,
    private val handshakeApiService: HandshakeApiService,
    private val imkbDao: ImkbDao,
) : ImkbRepository {
    override suspend fun getHandShake(request: HandshakeRequest) = getDataByNetwork { handshakeApiService.requestHandshake(request) }
    override suspend fun getRequestList(XVPAuthorization: String, request: ListRequest) = getDataByNetwork { imkbApiService.requestList(XVPAuthorization, request) }
    override suspend fun getRequestDetail(authStr: String, detailRequest: DetailRequest) = getDataByNetwork { imkbApiService.requestDetail(authStr, detailRequest) }
    override suspend fun insertDetailResponse(stock: Stock)= getDataByDatabase { imkbDao.insertStock(stock) }
    override suspend fun removeDetailResponse(stock: Stock): Flow<Resource<Unit>> = getDataByDatabase { imkbDao.deleteStock(stock) }
    override suspend fun getStock(id: Double) = getDataByDatabase { imkbDao.getStock(id) }
    override suspend fun getStocks() = getDataByDatabase { imkbDao.getStocks() }
}












