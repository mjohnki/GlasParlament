package de.glasparlament.agendaitem.overview

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
class AgendaItemViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemListUseCase>()

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
        val viewModel = AgendaItemViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)

        //then:
        Assert.assertFalse(viewModel.uiModel.value!!.listVisibility)
        Assert.assertTrue(viewModel.uiModel.value!!.progressBarVisibility)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val url = "http://test.test"
        val agendaItem16 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem16))
        coEvery { useCase.execute(url) } returns data
        val viewModel = AgendaItemViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)

        //then:
        //Loaded State
        Assert.assertTrue(viewModel.uiModel.value!!.listVisibility)
        Assert.assertFalse(viewModel.uiModel.value!!.progressBarVisibility)
        Assert.assertEquals(viewModel.uiModel.value!!.agendaItems, listOf(agendaItem16))
    }
}