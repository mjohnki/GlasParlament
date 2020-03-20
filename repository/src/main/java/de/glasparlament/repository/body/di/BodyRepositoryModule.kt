package de.glasparlament.repository.body.di

import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import de.glasparlament.data.BodyList
import de.glasparlament.repository.body.BodyApi
import de.glasparlament.repository.body.BodyEndpoint
import de.glasparlament.repository.body.BodyRepository
import de.glasparlament.repository.body.BodyRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class BodyRepositoryModule {

    @Provides
    @Singleton
    fun provideBodyEndpoint(retrofit: Retrofit): BodyEndpoint =
            retrofit.create(BodyEndpoint::class.java)

    @Provides
    @Singleton
    fun provideBodyApi(endpoint: BodyEndpoint) =
            BodyApi(endpoint)

    @Provides
    @Singleton
    fun provideBodyRepository(store: Store<String, BodyList>) =
            BodyRepositoryImpl(store) as BodyRepository

    @Provides
    @Singleton
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun provideBodyStore(api: BodyApi): Store<String, BodyList> =
            StoreBuilder
                    .fromNonFlow<String, BodyList> { api.getBodyList() }
                    .cachePolicy(
                            MemoryPolicy.builder()
                                    .setMemorySize(10)
                                    .setExpireAfterWrite(10)
                                    .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                                    .build()
                    ).build()
}
