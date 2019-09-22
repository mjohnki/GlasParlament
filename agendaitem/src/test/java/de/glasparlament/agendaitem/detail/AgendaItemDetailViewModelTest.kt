package de.glasparlament.agendaitem.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.glasparlament.agendaitem_repository.AgendaItem
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class AgendaItemDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemUseCase>()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
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
        Assert.assertNull(viewModel.uiModel.value!!.agendaItem)
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
        Assert.assertNotNull(viewModel.uiModel.value)
    }
}