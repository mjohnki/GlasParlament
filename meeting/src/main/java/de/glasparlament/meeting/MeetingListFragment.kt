package de.glasparlament.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.android.support.DaggerFragment
import de.glasparlament.common.DeepLink
import de.glasparlament.common.observe
import de.glasparlament.meetingRepository.Meeting
import kotlinx.android.synthetic.main.meeting_list_fragment.*
import javax.inject.Inject

class MeetingListFragment : DaggerFragment(), MeetingAdapter.OnItemClickListener {

    @Inject
    lateinit var adapter: MeetingAdapter

    @Inject
    lateinit var factory: MeetingViewModelFactory

    lateinit var viewModel: MeetingViewModel

    private val binder = MeetingViewStateBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, factory).get(MeetingViewModel::class.java)
        viewModel.bind(getString(R.string.url))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.meeting_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.listener = this
        val dividerItemDecoration = DividerItemDecoration(meetingList.context, DividerItemDecoration.VERTICAL)
        meetingList.addItemDecoration(dividerItemDecoration)
        observe(viewModel.state, ::updateUI)
    }

    override fun onPause() {
        super.onPause()
        adapter.listener = null
        meetingList.adapter = null
    }

    override fun onResume() {
        super.onResume()
        adapter.listener = this
        meetingList.adapter = adapter
    }

    private fun updateUI(state: MeetingViewModel.State) {
        binder(MeetingViewStateBinder.Params(
                state = state,
                adapter = adapter,
                searchAction = createOnSearchClickListener(),
                views = MeetingViewStateBinder.Views(
                        meetingList = meetingList,
                        progressBar = progressBar,
                        searchButton = search
                )
        ))
    }

    private fun createOnSearchClickListener(): View.OnClickListener =
            View.OnClickListener {
                findNavController().navigate(
                        DeepLink.search(resources)
                )
            }

    override fun onItemClick(meeting: Meeting) {
        findNavController().navigate(
                DeepLink.agendaOverview(
                        resources = resources,
                        title = meeting.name,
                        meetingId = meeting.id)
        )
    }
}
