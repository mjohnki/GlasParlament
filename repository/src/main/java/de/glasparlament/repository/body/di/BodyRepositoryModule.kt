package de.glasparlament.repository.body.di

import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import de.glasparlament.repository.body.Body
import de.glasparlament.repository.body.BodyRepository
import de.glasparlament.repository.body.BodyRepositoryImpl
import de.glasparlament.repository.body.remote.BodyApi
import de.glasparlament.repository.body.remote.BodyEndpoint
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
    fun provideBodyRepository(store: Store<String, List<Body>>) =
            BodyRepositoryImpl(store) as BodyRepository

    @Provides
    @Singleton
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun provideBodyStore(api: BodyApi): Store<String, List<Body>> =
            StoreBuilder
                    .fromNonFlow<String, List<Body>> { api.getBodyList() }
                    .cachePolicy(
                            MemoryPolicy.builder()
                                    .setMemorySize(MEMORY_SIZE)
                                    .setExpireAfterWrite(EXPIRE)
                                    .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                                    .build()
                    ).build()

    companion object {
        private const val MEMORY_SIZE = 10L
        private const val EXPIRE = 10L
    }
}
