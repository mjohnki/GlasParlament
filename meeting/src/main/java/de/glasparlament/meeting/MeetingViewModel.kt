package de.glasparlament.meeting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import de.glasparlament.meetingRepository.Meeting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MeetingViewModel : NavigationViewModel() {

    val state = MutableLiveData<State>()

    abstract fun bind(url: String)

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Loaded(val meetings: List<Meeting>) : State()
    }
}

class MeetingViewModelImpl(private val useCase: MeetingListUseCase) : MeetingViewModel() {

    override fun bind(url: String) {
        viewModelScope.launch {
            getMeetings(url)
        }
    }

    private suspend fun getMeetings(url: String) =
            withContext(Dispatchers.Default) {
                state.postValue(State.Loading)
                when (val result = useCase.execute(url)) {
                    is Transfer.Success -> {
                        state.postValue(State.Loaded(result.data))
                    }
                    is Transfer.Error -> {
                        state.postValue(State.Error)
                    }
                }
            }
}
