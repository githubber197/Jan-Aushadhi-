package com.janaushadhi.finder.data.network

import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.model.Store
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/medicines")
    suspend fun getMedicines(
        @Query("category") category: String? = null,
        @Query("q")        q: String? = null,
        @Query("limit")    limit: Int = 100,
        @Query("offset")   offset: Int = 0
    ): Response<MedicinesResponse>

    @GET("api/medicines/search")
    suspend fun searchMedicines(
        @Query("q") q: String
    ): Response<MedicinesResponse>

    @GET("api/medicines/{id}")
    suspend fun getMedicineById(
        @Path("id") id: Int
    ): Response<Medicine>

    @GET("api/stores")
    suspend fun getStores(): Response<StoresResponse>

    @GET("api/stores/nearby")
    suspend fun getNearbyStores(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int = 10
    ): Response<StoresResponse>

    @GET("api/categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("api/health")
    suspend fun health(): Response<HealthResponse>
}
