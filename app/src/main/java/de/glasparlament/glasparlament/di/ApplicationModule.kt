package de.glasparlament.glasparlament.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.glasparlament.AuthInterceptor
import de.glasparlament.glasparlament.BaseApplication
import de.glasparlament.glasparlament.MainActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ApplicationModule.Binding::class])
class ApplicationModule(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication {
        return application
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthInterceptor())
                .build()

        return Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://192.168.178.42/")
                .build()
    }

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeMainActivity(): MainActivity
    }
}