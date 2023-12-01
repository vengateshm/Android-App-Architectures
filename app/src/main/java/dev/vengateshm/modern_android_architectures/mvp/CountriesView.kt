package dev.vengateshm.modern_android_architectures.mvp

import dev.vengateshm.modern_android_architectures.mvc.CountryState

interface CountriesView {
    fun onStateChanged(countryState: CountryState)
}