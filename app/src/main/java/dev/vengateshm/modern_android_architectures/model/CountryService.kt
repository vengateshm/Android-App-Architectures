package dev.vengateshm.modern_android_architectures.model

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CountryService {

    private val client = HttpClient(Android) {
        // Logging
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("HTTP Client", message)
                }
            }
            level = LogLevel.ALL
        }
        // JSON
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
                encodeDefaults = false
            })
        }
        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    private val apiScope = CoroutineScope(Dispatchers.IO)

    fun getCountries(): Single<List<Country>> {
        return Single.create { emitter ->
            val job = apiScope.launch {
                try {
                    val countries = client.get("https://restcountries.com/v3.1/all") {

                    }.body<List<Country>>()
                    emitter.onSuccess(countries)
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }

            emitter.setCancellable {
                job.cancel()
            }
        }
    }
}