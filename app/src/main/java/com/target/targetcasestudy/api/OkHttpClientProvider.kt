package com.target.targetcasestudy.api

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpProvider @Inject constructor() {
    private val moshi = Moshi.Builder().build()

    private val okHttpClient by lazy {
        OkHttpClient
            .Builder()
            .build()
    }

    private val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

    private val retrofitClient by lazy {
        Retrofit.Builder().apply {
            addCallAdapterFactory(NetworkResponseAdapterFactory())
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(okHttpClient)
            baseUrl(BASE_URL)
        }.build()
    }

    internal val dealApi: DealApiKtx by lazy { retrofitClient.create(DealApiKtx::class.java) }
}