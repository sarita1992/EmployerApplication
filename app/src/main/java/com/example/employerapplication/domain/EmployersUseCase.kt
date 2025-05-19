package com.example.employerapplication.domain

import javax.inject.Inject

/**
 * Create By sarita
 */
class EmployersUseCase @Inject constructor(
    private val repository: EmployerRepository
) {
    suspend operator fun invoke(filter: String): List<Employer> {
        return repository.getEmployers(filter)
    }
}