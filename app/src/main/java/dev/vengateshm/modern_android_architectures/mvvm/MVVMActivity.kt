package dev.vengateshm.modern_android_architectures.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import dev.vengateshm.modern_android_architectures.composables.CountryScreen
import dev.vengateshm.modern_android_architectures.ui.theme.ModernAndroidArchitecturesTheme

class MVVMActivity : ComponentActivity() {

    private lateinit var viewModel: CountriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[CountriesViewModel::class.java]

        setContent {
            ModernAndroidArchitecturesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.data.observeAsState()
                    CountryScreen(state = state!!)
                }
            }
        }
    }
}