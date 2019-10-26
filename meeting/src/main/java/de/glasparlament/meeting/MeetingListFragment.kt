package de.glasparlament.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.glasparlament.common.DeepLink
import de.glasparlament.common.observe
import de.glasparlament.meetingRepository.Meeting
import kotlinx.android.synthetic.main.meeting_list_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MeetingListFragment : Fragment(), MeetingAdapter.OnItemClickListener {

    private val args: MeetingListFragmentArgs by navArgs()
    private val meetingViewModel: MeetingViewModel by viewModel()
    private val adapter = MeetingAdapter(this)
    private val binder = MeetingViewStateBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        meetingViewModel.bind(args.meetingListId)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = args.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.meeting_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(meetingViewModel.state, ::updateUI)
    }

    override fun onPause() {
        super.onPause()
        meeting_list.adapter = null
    }

    override fun onResume() {
        super.onResume()
        meeting_list.adapter = adapter
    }

    private fun updateUI(state: MeetingViewModel.State) {
        binder(MeetingViewStateBinder.Params(state, adapter,
                MeetingViewStateBinder.Views(meeting_list, progressBar)
        ))
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
