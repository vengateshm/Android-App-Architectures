package dev.vengateshm.modern_android_architectures.mvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import dev.vengateshm.modern_android_architectures.composables.CountryScreen
import dev.vengateshm.modern_android_architectures.mvc.CountryState
import dev.vengateshm.modern_android_architectures.ui.theme.ModernAndroidArchitecturesTheme

class MVPActivity : ComponentActivity(), CountriesView {

    private val data = MutableLiveData<CountryState>()
    private var presenter: CountriesPresenter? = CountriesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernAndroidArchitecturesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by data.observeAsState()
                    CountryScreen(state = state!!)
                }
            }
        }
    }

    override fun onStateChanged(countryState: CountryState) {
        data.value = countryState
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        presenter = null
    }
}