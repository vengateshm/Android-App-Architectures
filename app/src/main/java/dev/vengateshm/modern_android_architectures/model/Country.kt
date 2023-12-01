package dev.vengateshm.modern_android_architectures.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName("name") val name: CountryName
)

@Serializable
data class CountryName(
    @SerialName("common") val common: String,
    @SerialName("official") val official: String
)