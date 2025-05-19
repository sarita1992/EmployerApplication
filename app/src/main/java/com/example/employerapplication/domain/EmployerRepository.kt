package com.example.employerapplication.domain

/**
 * Create By sarita
 */
interface EmployerRepository {
    suspend fun getEmployers(filter: String): List<Employer>
}