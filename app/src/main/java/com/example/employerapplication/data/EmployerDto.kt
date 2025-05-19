package com.example.employerapplication.data

import com.google.gson.annotations.SerializedName

/**
 * Create By sarita
 */
data class EmployerDto(
    @SerializedName("Name") val employerName: String,
    @SerializedName("Id") val employerId: Int,
    @SerializedName("DiscountPercentage") val discountPercentage: Double,
    @SerializedName("Place") val employerPlace: String
)
