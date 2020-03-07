package de.glasparlament.meeting

import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.repository.meeting.Meeting
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class MeetingViewBinderTest {

    private val binder = MeetingViewStateBinder()
    private val adapter = mockk<MeetingAdapter>(relaxed = true)
    private val searchAction = mockk<View.OnClickListener>(relaxed = true)
    private val agendaList = mockk<RecyclerView>(relaxed = true)
    private val progressBar = mockk<View>(relaxed = true)
    private val searchButton = mockk<ImageButton>(relaxed = true)
    private val views = MeetingViewStateBinder.Views(agendaList, progressBar, searchButton)

    @Test
    fun testSuccessWithStateLoaded() {
        //given:
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        val model = MeetingViewModel.State.Loaded(listOf(meeting))
        val params = MeetingViewStateBinder.Params(model, searchAction, adapter, views)

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
        val model = MeetingViewModel.State.Loading
        val params = MeetingViewStateBinder.Params(model, searchAction, adapter, views)

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
        val model = MeetingViewModel.State.Error
        val params = MeetingViewStateBinder.Params(model, searchAction, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }
}
