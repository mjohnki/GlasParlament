package de.glasparlament.agendaitem.detail

import de.glasparlament.agendaitem.InstantExecutorExtension
import de.glasparlament.repository.agendaItem.AgendaItem
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
class AgendaItemDetailViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemUseCase>()

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
        val url = "http://test.test"
        val errorMessage = "Error Loading Data"
        val data = Transfer.Error(errorMessage)
        coEvery { useCase.execute(url) } returns data
        val viewModel = AgendaItemDetailViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)

        //then:
        Assertions.assertTrue(viewModel.state.value is AgendaItemDetailViewModel.State.Error)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val url = "http://test.test"
        val data = Transfer.Success(agendaItem)
        coEvery { useCase.execute(url) } returns data
        val viewModel = AgendaItemDetailViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)

        //then:
        Assertions.assertTrue(viewModel.state.value is AgendaItemDetailViewModel.State.Loaded)
    }
}