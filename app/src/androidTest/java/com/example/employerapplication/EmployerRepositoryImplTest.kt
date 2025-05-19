package com.example.employerapplication

import com.example.employerapplication.data.EmployerApiService
import com.example.employerapplication.data.EmployerDto
import com.example.employerapplication.data.EmployerRepositoryImpl
import com.example.employerapplication.domain.Employer
import com.example.employerapplication.domain.EmployerRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Create By sarita
 */

@ExperimentalCoroutinesApi
class EmployerRepositoryImplTest {
    private lateinit var repository: EmployerRepository
    private val apiService: EmployerApiService = mockk()

    @Before
    fun setUp() {
        repository = EmployerRepositoryImpl(apiService)
    }

    @Test
    fun getEmployer() = runTest {
        // Given
        val filter = "Achmea"
        val apiResponse = listOf(
            EmployerDto("Achmea Group", 1, 10.0, "Amsterdam"),
            EmployerDto("Achmea HR", 2, 15.0, "Rotterdam")
        )

        coEvery { apiService.getEmployers(filter, 100) } returns apiResponse

        // When
        val result = repository.getEmployers(filter)

        // Then
        val expected = listOf(
            Employer("Achmea Group", 1, 10.0, "Amsterdam"),
            Employer("Achmea HR", 2, 15.0, "Rotterdam")
        )

        assertEquals(expected, result)
    }

    @Test
    fun getEmployersThrowsException() = runTest {
        // Given
        val filter = "Achmea"
        val exception = RuntimeException("API failure")
        coEvery { apiService.getEmployers(filter, 100) } throws exception

        // When & Then
        try {
            repository.getEmployers(filter)
            assert(false) { "Expected exception was not thrown" }
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }
}