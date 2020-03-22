package de.glasparlament.agendaitem.overview

import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.agendaitem.InstantExecutorExtension
import de.glasparlament.agendaitem.getTestValue
import de.glasparlament.repository.agendaItem.AgendaItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class AgendaItemViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemListUseCase>()
    private val viewModel = AgendaItemViewModelImpl(useCase)

    @BeforeEach
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_UseCase_returns_data_state() {
        //given:
        val url = "http://test.test"
        val agendaItem16 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        coEvery { useCase.execute(url) } returns flow {
            emit(StoreResponse.Data(listOf(agendaItem16), ResponseOrigin.Fetcher))
        }

        //when:
        viewModel.bind(url)

        //then:
        Assertions.assertTrue(viewModel.state.getTestValue() is AgendaItemViewModel.State.Loaded)
    }

    @Test
    fun test_UseCase_returns_loading_state() {
        //given:
        val url = "http://test.test"
        coEvery { useCase.execute(url) } returns flow {
            emit(StoreResponse.Loading<List<AgendaItem>>(ResponseOrigin.Fetcher))
        }

        //when:
        viewModel.bind(url)

        //then:
        Assertions.assertTrue(viewModel.state.getTestValue() is AgendaItemViewModel.State.Loading)
    }

    @Test
    fun test_UseCase_returns_error_state() {
        //given:
        val url = "http://test.test"
        coEvery { useCase.execute(url) } returns flow {
            emit(StoreResponse.Error<List<AgendaItem>>(IllegalArgumentException(), ResponseOrigin.Fetcher))
        }

        //when:
        viewModel.bind(url)

        //then:
        Assertions.assertTrue(viewModel.state.getTestValue() is AgendaItemViewModel.State.Error)
    }
}