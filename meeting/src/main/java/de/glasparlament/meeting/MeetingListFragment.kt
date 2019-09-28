package de.glasparlament.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.glasparlament.common.NavigationCommand
import de.glasparlament.meeting.databinding.MeetingListFragmentBinding
import de.glasparlament.meetingRepository.Meeting
import org.koin.android.viewmodel.ext.android.viewModel

class MeetingListFragment : Fragment(), MeetingAdapter.OnItemClickListener {

    private val args : MeetingListFragmentArgs by navArgs()

    val meetingViewModel: MeetingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        meetingViewModel.bind(args.meetingListId)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = args.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val adapter = MeetingAdapter(this)
        val binding = DataBindingUtil.inflate<MeetingListFragmentBinding>(
                inflater, R.layout.meeting_list_fragment, container, false)
        binding.viewModel = meetingViewModel
        binding.lifecycleOwner = this
        binding.meetingList.adapter = adapter

        subscribe(meetingViewModel, adapter)

        meetingViewModel.navigationCommand.observe(this, Observer { command ->
            command.getContentIfNotHandled()?.let{
                when (it) {
                    is NavigationCommand.To -> findNavController().navigate(it.directions)
                }
            }
        })

        return binding.root
    }

    private fun subscribe(viewModel: MeetingViewModel, adapter: MeetingAdapter) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer { model ->
            adapter.submitList(model.meetings)
        })
    }

    override fun onItemClick(meeting: Meeting) {
        val direction =
                MeetingListFragmentDirections.actionMeetingListFragmentToAgendaFragment(meeting.id, meeting.name)
        meetingViewModel.navigate(direction)
    }
}
