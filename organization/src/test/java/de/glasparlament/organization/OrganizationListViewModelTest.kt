package de.glasparlament.organization

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

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
        // Check State Loading
        viewModel.uiModel.observeOnce {
            Assert.assertFalse(it.listVisibility)
            Assert.assertTrue(it.progressBarVisibility)
        }

        //when:
        viewModel.loadData()

        //then:
        viewModel.uiModel.observeOnce {
            Assert.assertFalse(it.listVisibility)
            Assert.assertTrue(it.progressBarVisibility)
        }
    }

    @Test
    fun testUseCaseWithBodyListSuccess() {
        //given:
        val data = Transfer.Success(TestData.bodyOrganizations)
        coEvery { useCase.execute() } returns data
        viewModel = OrganizationListViewModelImpl(useCase)
        // Check Loading State
        viewModel.uiModel.observeOnce {
            Assert.assertFalse(it.listVisibility)
            Assert.assertTrue(it.progressBarVisibility)
        }

        //when:
        viewModel.loadData()
        Thread.sleep(1000)

        //then:
        //Loaded State
        viewModel.uiModel.observeOnce {
            Assert.assertTrue(it.listVisibility)
            Assert.assertFalse(it.progressBarVisibility)
            Assert.assertEquals(it.organizations, TestData.bodyOrganizations)
        }
    }

    @Test
    fun testUseCaseNavigate() {
        //given:
        val data = Transfer.Success(TestData.bodyOrganizations)
        coEvery { useCase.execute() } returns data
        val direction =
                OrganizationListFragmentDirections.actionOrganizationListFragmentToMeetingListFragment("meeting", "name")
        viewModel = OrganizationListViewModelImpl(useCase)

        //when:
        viewModel.navigate(direction)

        //then:
        viewModel.navigationCommand.observeOnce {
            Assert.assertTrue(it is NavigationCommand.To)
        }
    }
}

class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)
    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}