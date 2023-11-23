package com.ravinada.riomoneyassignment.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ravinada.riomoneyassignment.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Builder class for creating Retrofit instances with a specified base URL and API type.
 */
object NetworkBuilder {

    const val BASE_URL = BuildConfig.BASE_URL

    /**
     * Create a Retrofit instance for the specified API type.
     *
     * @param baseUrl The base URL for the API.
     * @param apiType The type of the API interface.
     * @return The created API instance.
     */
    fun <T> create(baseUrl: String, apiType: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(gmHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(apiType)

    /**
     * Create an OkHttpClient with custom configurations.
     *
     * @return The configured OkHttpClient instance.
     */
    private fun gmHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Set connection, write, and read timeouts
        builder.connectTimeout(2, TimeUnit.MINUTES)
        builder.writeTimeout(2, TimeUnit.MINUTES)
        builder.readTimeout(2, TimeUnit.MINUTES)

        // Add logging interceptor for debugging
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }).retryOnConnectionFailure(true)

        // Add a custom interceptor if needed
        builder.addInterceptor { chain ->
            var request = chain.request()
            val requestBuilder = request.newBuilder()
            request = requestBuilder.build()
            chain.proceed(request)
        }

        return builder.build()
    }
}
