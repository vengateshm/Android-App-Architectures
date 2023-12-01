package dev.vengateshm.modern_android_architectures.mvc

import dev.vengateshm.modern_android_architectures.model.Country
import dev.vengateshm.modern_android_architectures.model.CountryService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CountriesController(private val view: MVCActivity) {

    private val countryService = CountryService()
    private val disposable = CompositeDisposable()

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        view.onStateChanged(CountryState(isLoading = true))
        disposable.add(
            countryService.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.onStateChanged(
                            CountryState(
                                countries = it,
                                error = null,
                                isLoading = false
                            )
                        )
                    },
                    {
                        view.onStateChanged(CountryState(error = it.toString(), isLoading = false))
                    })
        )
    }

    fun onDestroy() {
        if (!disposable.isDisposed) {
            disposable.clear()
        }
    }
}

data class CountryState(
    val countries: List<Country> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)