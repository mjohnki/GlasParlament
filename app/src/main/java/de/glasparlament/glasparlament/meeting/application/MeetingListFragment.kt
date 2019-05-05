package de.glasparlament.glasparlament.meeting.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import de.glasparlament.glasparlament.MainActivity
import de.glasparlament.glasparlament.R
import de.glasparlament.glasparlament.databinding.MeetingListFragmentBinding
import javax.inject.Inject

class MeetingListFragment : DaggerFragment() {

    @Inject
    lateinit var factory: MeetingViewModelFactory

    private val args: MeetingListFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val viewModel = ViewModelProviders.of(this, factory).get(MeetingViewModel::class.java)
        viewModel.bind(args.meetingListId)

        val binding = DataBindingUtil.inflate<MeetingListFragmentBinding>(
                inflater, R.layout.meeting_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val adapter = MeetingAdapter()

        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar!!.title = args.title

        binding.meetingList.adapter = adapter
        subscribeUi(adapter, viewModel)

        return binding.root
    }

    private fun subscribeUi(adapter: MeetingAdapter, meetingListViewModel: MeetingViewModel) {
        meetingListViewModel.meetings.observe(viewLifecycleOwner, Observer { meetings ->
            if (meetings != null) {
                adapter.submitList(meetings)
            }
        })
    }
}
