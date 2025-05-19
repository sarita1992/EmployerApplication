package com.example.employerapplication

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.employerapplication.domain.Employer
import com.example.employerapplication.domain.EmployersUseCase
import com.example.employerapplication.domain.ResultState
import com.example.employerapplication.presentation.EmployerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class EmployerViewModelTest {
    private lateinit var viewModel: EmployerViewModel
    private val employersUseCase: EmployersUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { employersUseCase.invoke(any()) } returns emptyList()
        viewModel = EmployerViewModel(employersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emitSuccessForEmployerState() = runTest {
        val fakeList = listOf(Employer("Achmea Group",0,0.5,"NetherLands"), Employer("Achmea Group",1,5.0,"Austria"))
        coEvery { employersUseCase.invoke("Achmea") } returns fakeList
        viewModel = EmployerViewModel(employersUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is ResultState.Success)
        assertEquals(fakeList, (state as ResultState.Success).data)
    }

    @Test
    fun emitNoDataFoundState() = runTest {
        coEvery { employersUseCase.invoke("Achmea") } returns emptyList()
        viewModel = EmployerViewModel(employersUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(ResultState.Empty, state)
    }

    @Test
    fun emitErrorWhenExceptionState() = runTest {
        coEvery { employersUseCase.invoke("Achmea") } throws RuntimeException("Network error")

        viewModel = EmployerViewModel(employersUseCase)
        advanceUntilIdle()
        val state = viewModel.state.value
        assertTrue(state is ResultState.Error)
        assertTrue((state as ResultState.Error).message.contains("Network error"))
    }

    @Test
    fun triggerSearchAndUpdate() = runTest {
        val fakeList = listOf(Employer("Google",0,10.00,"NetherLands"))
        coEvery { employersUseCase.invoke("Google") } returns fakeList
        viewModel.onSearchChange("Google")
        advanceTimeBy(300)
        advanceUntilIdle()
        val state = viewModel.state.value
        assertTrue(state is ResultState.Success)
        assertEquals(fakeList, (state as ResultState.Success).data)
    }

}