package de.glasparlament.glasparlament.di

import de.glasparlament.glasparlament.AuthInterceptor
import de.glasparlament.glasparlament.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    single<Retrofit> { provideRetrofit() }
}

fun provideRetrofit(): Retrofit {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()

    return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
}
