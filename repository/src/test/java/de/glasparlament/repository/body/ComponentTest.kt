package de.glasparlament.repository.body

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.body.di.DaggerTestApplicationComponent
import de.glasparlament.repository.body.remote.BodyEndpoint
import de.glasparlament.repository.body.remote.BodyList
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ComponentTest {

    @Inject
    lateinit var repository: BodyRepository

    @Inject
    lateinit var endpoint: BodyEndpoint

    @BeforeEach
    fun setUp() {
        DaggerTestApplicationComponent.builder()
                .build().inject(this)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        coEvery { endpoint.getBodyList() } returns BodyList()
        var receivedDataResponse = false
        var receivedLoadingResponse = false

        //when:
       runBlocking {
            repository.getBodyList().take(2).collect {
                when(it){
                    is StoreResponse.Data -> receivedDataResponse = true
                    is StoreResponse.Loading -> receivedLoadingResponse = true
                }
            }
        }

        //then:
        coVerify { endpoint.getBodyList() }
        assert(receivedDataResponse)
        assert(receivedLoadingResponse)
    }
}
