package com.example.employerapplication.domain

/**
 * Create By sarita
 */
sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    object Empty : ResultState<Nothing>()
    data class Error(val message: String) : ResultState<Nothing>()
}