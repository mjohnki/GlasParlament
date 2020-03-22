package de.glasparlament.search

import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.search.useCase.SearchUseCase
import de.glasparlament.search.vm.SearchViewModel
import de.glasparlament.search.vm.SearchViewModelImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class SearchViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<SearchUseCase>()
    private val viewModel = SearchViewModelImpl(useCase)

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
    fun test_UseCase_should_set_state_to_Loaded() {
        testDispatcher.runBlockingTest {
            //given:
            val agendaItem = AgendaItemSearchResult(
                    id = "id",
                    name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                    number = "16",
                    meetingName = "meeting")
            val search = "holz"
            coEvery { useCase.execute(search) } returns flow<StoreResponse<List<AgendaItemSearchResult>>> {
                emit(StoreResponse.Data(listOf(agendaItem), ResponseOrigin.Fetcher))
            }

            //when:
            viewModel.search(search)

            //then:
            Assertions.assertTrue(viewModel.state.getTestValue() is SearchViewModel.State.Loaded)
        }
    }

    @Test
    fun test_UseCase_should_set_state_to_Loading() {
        testDispatcher.runBlockingTest {
            //given:
            val search = "holz"
            coEvery { useCase.execute(search) } returns flow<StoreResponse<List<AgendaItemSearchResult>>> {
                emit(StoreResponse.Loading(ResponseOrigin.Fetcher))
            }

            //when:
            viewModel.search(search)

            //then:
            Assertions.assertTrue(viewModel.state.getTestValue() is SearchViewModel.State.Loading)
        }
    }

    @Test
    fun test_UseCase_should_set_state_to_Error() {
        testDispatcher.runBlockingTest {
            //given:
            val search = "holz"
            coEvery { useCase.execute(search) } returns flow<StoreResponse<List<AgendaItemSearchResult>>> {
                emit(StoreResponse.Error(origin = ResponseOrigin.Fetcher, error = IllegalArgumentException()))
            }

            //when:
            viewModel.search(search)

            //then:
            Assertions.assertTrue(viewModel.state.getTestValue() is SearchViewModel.State.Error)
        }
    }
}