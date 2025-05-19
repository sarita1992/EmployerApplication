package com.example.employerapplication.data

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create By sarita
 */
interface EmployerApiService {
    @GET("employers")
    suspend fun getEmployers(
        @Query("filter") filter: String,
        @Query("maxRows") maxRows: Int,
    ): List<EmployerDto>
}