package dev.vengateshm.modern_android_architectures.mvc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import dev.vengateshm.modern_android_architectures.composables.CountryList
import dev.vengateshm.modern_android_architectures.ui.theme.ModernAndroidArchitecturesTheme

class MVCActivity : ComponentActivity() {

    private val data = MutableLiveData<CountryState>()
    private val controller = CountriesController(this)

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
                    if (state!!.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    if (state!!.error != null) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error : ${state!!.error}")
                        }
                    }
                    if (state!!.countries.isNotEmpty()) {
                        CountryList(countries = state!!.countries)
                    } else if (!state!!.isLoading && state!!.error == null) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No countries found")
                        }
                    }
                }
            }
        }
    }

    fun onStateChanged(countryState: CountryState) {
        data.value = countryState
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}