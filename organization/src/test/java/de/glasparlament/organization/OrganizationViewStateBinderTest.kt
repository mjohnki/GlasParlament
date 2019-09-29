package de.glasparlament.organization

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class OrganizationViewStateBinderTest {

    private val binder = OrganizationViewStateBinder()
    private val adapter = mockk<OrganizationAdapter>(relaxed = true)
    private val agendaList = mockk<RecyclerView>(relaxed = true)
    private val progressBar = mockk<View>(relaxed = true)
    private val views = OrganizationViewStateBinder.Views(agendaList, progressBar)

    @Test
    fun testSuccessWithStateLoaded() {
        //given:
        val model = OrganizationListViewModel.State.Loaded(listOf(TestData.bodyOrganization))
        val params = OrganizationViewStateBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.GONE }
        verify { agendaList.visibility = View.VISIBLE }
        verify { adapter.submitList(model.meetings) }
    }

    @Test
    fun testSuccessWithStateLoading() {
        //given:
        val model = OrganizationListViewModel.State.Loading
        val params = OrganizationViewStateBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }

    @Test
    fun testSuccessWithStateError() {
        //given:
        val model = OrganizationListViewModel.State.Error
        val params = OrganizationViewStateBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }
}
