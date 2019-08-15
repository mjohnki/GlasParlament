package de.glasparlament.meeting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.Transfer
import de.glasparlament.data.Meeting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MeetingViewModel : ViewModel() {
    abstract fun bind(url: String)

    val uiModel = MutableLiveData<UIModel>()
    val navigationCommand = MutableLiveData<NavigationCommand>()

    abstract fun navigate(directions: NavDirections)

    companion object {
        fun loading() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun error() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun loaded(meetings: List<Meeting>) = UIModel(progressBarVisibility = false, listVisibility = true, meetings = meetings)
    }
}

class MeetingViewModelImpl(private val useCase: MeetingListUseCase) : MeetingViewModel() {

    override fun bind(url: String) {
        viewModelScope.launch {
            getMeetings(url)
        }
    }

    private suspend fun getMeetings(url: String) = withContext(Dispatchers.Default) {
        uiModel.postValue(loading())
        when (val result = useCase.execute(url)) {
            is Transfer.Success -> {
                uiModel.postValue(loaded(result.data))
            }
            is Transfer.Error -> {
                uiModel.postValue(error())
            }
        }
    }

    override fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }
}

data class UIModel(
        val progressBarVisibility: Boolean,
        val listVisibility: Boolean,
        val meetings: List<Meeting> = emptyList())