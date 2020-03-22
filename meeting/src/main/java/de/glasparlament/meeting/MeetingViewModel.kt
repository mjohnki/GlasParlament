package de.glasparlament.meeting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.meeting.Meeting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MeetingViewModel : ViewModel() {

    val state = MutableLiveData<State>(State.Initial)

    abstract fun bind(url: String)

    sealed class State {
        object Initial : State()
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

    private suspend fun getMeetings(url: String) {
        withContext(Dispatchers.IO) {
            useCase.execute(url).collect { data ->
                when (data) {
                    is StoreResponse.Loading -> state.postValue(State.Loading)
                    is StoreResponse.Data -> state.postValue(State.Loaded(data.value))
                    is StoreResponse.Error -> state.postValue(State.Error)
                }
            }
        }
    }
}
