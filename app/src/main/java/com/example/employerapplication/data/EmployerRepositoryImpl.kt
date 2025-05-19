package com.example.employerapplication.data

import com.example.employerapplication.domain.Employer
import com.example.employerapplication.domain.EmployerRepository
import javax.inject.Inject

/**
 * Create By sarita
 */
class EmployerRepositoryImpl @Inject constructor(
    private val api: EmployerApiService,
) : EmployerRepository {
    override suspend fun getEmployers(filter: String): List<Employer> {
        return api.getEmployers(filter, 100).map {
            Employer(it.employerName, it.employerId, it.discountPercentage, it.employerPlace)
        }
    }
}