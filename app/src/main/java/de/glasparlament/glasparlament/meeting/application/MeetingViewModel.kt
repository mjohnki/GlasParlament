package de.glasparlament.glasparlament.meeting.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.glasparlament.glasparlament.ext.addTo
import de.glasparlament.glasparlament.meeting.data.Meeting
import de.glasparlament.glasparlament.meeting.domain.MeetingListUseCase
import de.glasparlament.glasparlament.meeting.domain.MeetingListUseCase.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MeetingViewModel(private val useCase: MeetingListUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()
    val meetings = MutableLiveData<List<Meeting>>()
    private val showErrorGettingOrganizations = MutableLiveData<Boolean>()
    val progressVisible = MutableLiveData<Boolean>()



    fun bind(url: String) {
        useCase.execute(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleGetMeetingListResult(it) }
                .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun handleGetMeetingListResult(result: Result) {
        progressVisible.postValue(result == Result.Loading)
        when (result) {
            is Result.Success -> {
                meetings.postValue(result.data)
            }
            is Result.Failure -> {
                showErrorGettingOrganizations.postValue(true)
            }
        }
    }
}