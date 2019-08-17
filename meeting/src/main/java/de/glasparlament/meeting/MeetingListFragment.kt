package de.glasparlament.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.glasparlament.common_android.NavigationFragment
import de.glasparlament.common_android.NavigationViewModel
import de.glasparlament.data.Meeting
import de.glasparlament.meeting.databinding.MeetingListFragmentBinding
import javax.inject.Inject

class MeetingListFragment : NavigationFragment(), MeetingAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: MeetingViewModelFactory

    private val args by navArgs<MeetingListFragmentArgs>()

    lateinit var viewModel: MeetingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(MeetingViewModel::class.java)
        viewModel.bind(args.meetingListId)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = args.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val adapter = MeetingAdapter(this)
        val binding = DataBindingUtil.inflate<MeetingListFragmentBinding>(
                inflater, R.layout.meeting_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.meetingList.adapter = adapter

        subscribe(viewModel, adapter)

        return binding.root
    }

    private fun subscribe(viewModel: MeetingViewModel, adapter: MeetingAdapter) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer { model ->
            adapter.submitList(model.meetings)
        })
    }

    override fun getViewModel(): NavigationViewModel =
            viewModel

    override fun onItemClick(meeting: Meeting) {
        val direction =
                MeetingListFragmentDirections.actionMeetingListFragmentToAgendaFragment(meeting.id, meeting.name)
        viewModel.navigate(direction)
    }
}
