package de.glasparlament.glasparlament.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.data.db.GPDatabase
import de.glasparlament.glasparlament.AuthInterceptor
import de.glasparlament.glasparlament.BaseApplication
import de.glasparlament.glasparlament.BuildConfig
import de.glasparlament.glasparlament.MainActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
    }

    @Provides
    @Singleton
    fun provideDB(app : BaseApplication) : GPDatabase {
        return Room.databaseBuilder(
                app.applicationContext,
                GPDatabase::class.java,
                "Database"
        ).build()
    }

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeMainActivity(): MainActivity
    }
}