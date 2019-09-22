package de.glasparlament.organization

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
class OrganizationListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<OrganizationListUseCase>()
    private lateinit var viewModel: OrganizationListViewModel

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
        Assertions.assertFalse(viewModel.uiModel.value!!.listVisibility)
        Assertions.assertTrue(viewModel.uiModel.value!!.progressBarVisibility)
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
        Assertions.assertTrue(viewModel.uiModel.value!!.listVisibility)
        Assertions.assertFalse(viewModel.uiModel.value!!.progressBarVisibility)
        Assertions.assertEquals(viewModel.uiModel.value!!.organizations, TestData.bodyOrganizations)
    }
}

