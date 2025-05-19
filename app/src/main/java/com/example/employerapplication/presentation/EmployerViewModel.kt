package com.example.employerapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employerapplication.domain.Employer
import com.example.employerapplication.domain.EmployersUseCase
import com.example.employerapplication.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Create By sarita
 */

@HiltViewModel
class EmployerViewModel @Inject constructor(private val employersUseCase: EmployersUseCase) : ViewModel() {
    private val _resultState = MutableStateFlow<ResultState<List<Employer>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Employer>>> = _resultState
    var searchQueryState = MutableStateFlow("Achmea")
    val searchQuery: StateFlow<String> = searchQueryState

    init {
        searchQuery()
    }

    private suspend fun getEmployers(searchQuery: String) {
        _resultState.value = ResultState.Loading
        try {
            val result = employersUseCase(searchQuery)
                .filter { it.employerName.contains(searchQuery, ignoreCase = true) }
            if (result.isEmpty()) {
                _resultState.value = ResultState.Empty
            } else {
                _resultState.value = ResultState.Success(result)
            }
        } catch (e: Exception) {
            _resultState.value = ResultState.Error("Failed to load employers: ${e.localizedMessage}")
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchQuery() {
        viewModelScope.launch {
            searchQueryState
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    getEmployers(query)
                }
        }
    }

    fun onSearchChange(query: String) {
        searchQueryState.value = query
    }

}