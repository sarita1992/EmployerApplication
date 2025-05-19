package com.example.employerapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employerapplication.domain.Employer
import com.example.employerapplication.domain.EmployersUseCase
import com.example.employerapplication.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Create By sarita
 */

@HiltViewModel
class EmployerViewModel @Inject constructor(private val employersUseCase: EmployersUseCase) : ViewModel() {
    private val _resultState = MutableStateFlow<ResultState<List<Employer>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Employer>>> = _resultState
    var searchQuery by mutableStateOf("Achmea")

    init {
        getEmployers()
    }

    fun getEmployers() {
        viewModelScope.launch {
            _resultState.value = ResultState.Loading
            try {
                val result = employersUseCase(searchQuery).filter { it.employerName.contains(searchQuery, ignoreCase = true) }
                if (result.isEmpty()) {
                    _resultState.value = ResultState.Empty
                } else {
                    _resultState.value = ResultState.Success(result)
                }
            } catch (e: Exception) {
                _resultState.value = ResultState.Error("Failed to load employers: ${e.localizedMessage}")
            }
        }
    }

    fun onSearchChange(query: String) {
        searchQuery = query
        getEmployers()
    }

}