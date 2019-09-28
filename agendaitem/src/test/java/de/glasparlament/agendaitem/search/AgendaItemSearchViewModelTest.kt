package de.glasparlament.agendaitem.search

import de.glasparlament.agendaitem.InstantExecutorExtension
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class AgendaItemSearchViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemSearchUseCase>()

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
    fun testUseCaseError() {
        //given:
        val search = "holz"
        val errorMessage = "Error Loading Data"
        val data = Transfer.Error(errorMessage)
        coEvery { useCase.execute(search) } returns data
        val viewModel = AgendaItemSearchViewModelImpl(useCase)

        //when:
        viewModel.search(search)
        Thread.sleep(200)

        //then:
        Assertions.assertTrue(viewModel.state.value is AgendaItemSearchViewModel.State.Error)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")
        val search = "holz"
        val data = Transfer.Success(listOf(agendaItem))
        coEvery { useCase.execute(search) } returns data
        val viewModel = AgendaItemSearchViewModelImpl(useCase)

        //when:
        viewModel.search(search)
        Thread.sleep(200)

        //then:
        Assertions.assertTrue(viewModel.state.value is AgendaItemSearchViewModel.State.Loaded)
    }
}