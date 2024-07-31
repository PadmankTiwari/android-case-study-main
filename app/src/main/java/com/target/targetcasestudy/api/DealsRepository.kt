package com.target.targetcasestudy.api

import javax.inject.Inject

class DealsRepository @Inject constructor(
    okHttpProvider: OkHttpProvider
) {
    private val service = okHttpProvider.dealApi

    suspend fun retrieveDeals(): DealResponse? {
        val response  = service.retrieveDeals()
        if(response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Failed to retrieve deals")
        }
    }

    suspend fun retrieveItemDetails(id: String): Deal? {
        val response  = service.retrieveDeal(id)
        if(response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Failed to retrieve deals")
        }
    }
}