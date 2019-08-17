package de.glasparlament.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import de.glasparlament.common_android.NavigationFragment
import de.glasparlament.common_android.NavigationViewModel
import de.glasparlament.data.Meeting
import de.glasparlament.meeting.databinding.MeetingListFragmentBinding
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class MeetingListFragment : NavigationFragment() {

    @Inject
    lateinit var factory: MeetingViewModelFactory

    private val oneAdapter: OneAdapter = OneAdapter()

    private val args by navArgs<MeetingListFragmentArgs>()

    lateinit var viewModel : MeetingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(MeetingViewModel::class.java)
        viewModel.bind(args.meetingListId)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = args.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<MeetingListFragmentBinding>(
                inflater, R.layout.meeting_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        oneAdapter.attachItemModule(MeetingItemModule()
                .addEventHook(ItemClickEvent(viewModel)))
        oneAdapter.attachTo(binding.meetingList)

        subscribe(viewModel)

        return binding.root
    }

    private fun subscribe(viewModel: MeetingViewModel) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer { model ->
            oneAdapter.add(model.meetings)
        })
    }

    override fun getViewModel(): NavigationViewModel =
            viewModel
}

private class ItemClickEvent(val viewModel: MeetingViewModel) : ClickEventHook<Meeting>() {

    override fun onClick(@NonNull model: Meeting, @NonNull viewBinder: ViewBinder) {
        val direction = MeetingListFragmentDirections.actionMeetingListFragmentToAgendaFragment(model.id, model.name)
        viewModel.navigate(direction)
    }
}


private class MeetingItemModule : ItemModule<Meeting>() {

    @NotNull
    override fun provideModuleConfig(): ItemModuleConfig {
        return object : ItemModuleConfig() {
            override fun withLayoutResource(): Int {
                return R.layout.meeting_list_item
            }
        }
    }

    override fun onBind(@NotNull model: Meeting, @NotNull viewBinder: ViewBinder) {
        val organizationName: TextView = viewBinder.findViewById(R.id.meetingName)
        organizationName.text = model.name
    }
}
