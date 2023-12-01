package dev.vengateshm.modern_android_architectures.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.vengateshm.modern_android_architectures.model.CountryService
import dev.vengateshm.modern_android_architectures.mvc.CountryState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CountriesViewModel : ViewModel() {

    private val countryService = CountryService()
    private val disposable = CompositeDisposable()
    private val _data = MutableLiveData<CountryState>()
    val data: LiveData<CountryState> = _data

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        _data.value = CountryState(isLoading = true)
        disposable.add(
            countryService.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _data.value =
                            CountryState(
                                countries = it,
                                error = null,
                                isLoading = false
                            )
                    },
                    {
                        _data.value = CountryState(error = it.toString(), isLoading = false)
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.clear()
        }
    }
}