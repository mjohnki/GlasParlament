package de.glasparlament.organization

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
class OrganizationListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<OrganizationListUseCase>()
    private lateinit var viewModel: OrganizationListViewModel

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
    fun testUseCaseWithBodyListError() {
        //given:
        val errorMessage = "Error Loading Data"
        val data = Transfer.Error(errorMessage)
        coEvery { useCase.execute() } returns data
        viewModel = OrganizationListViewModelImpl(useCase)

        //when:
        viewModel.loadData()
        Thread.sleep(200)

        //then:
        Assert.assertFalse(viewModel.uiModel.value!!.listVisibility)
        Assert.assertTrue(viewModel.uiModel.value!!.progressBarVisibility)
    }

    @Test
    fun testUseCaseWithBodyListSuccess() {
        //given:
        val data = Transfer.Success(TestData.bodyOrganizations)
        coEvery { useCase.execute() } returns data
        viewModel = OrganizationListViewModelImpl(useCase)

        //when:
        viewModel.loadData()
        Thread.sleep(200)

        //then:
        Assert.assertTrue(viewModel.uiModel.value!!.listVisibility)
        Assert.assertFalse(viewModel.uiModel.value!!.progressBarVisibility)
        Assert.assertEquals(viewModel.uiModel.value!!.organizations, TestData.bodyOrganizations)
    }
}

