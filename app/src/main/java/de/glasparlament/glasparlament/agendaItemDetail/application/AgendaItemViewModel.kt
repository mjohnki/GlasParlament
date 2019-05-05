package de.glasparlament.glasparlament.agendaItemDetail.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import de.glasparlament.glasparlament.agendaItemDetail.domain.AgendaItemUseCase
import de.glasparlament.glasparlament.agendaItemDetail.domain.AgendaItemUseCase.Result
import de.glasparlament.glasparlament.ext.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AgendaItemViewModel(private val useCase: AgendaItemUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()
    val agendaItem = MutableLiveData<AgendaItem>()
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
                agendaItem.postValue(result.data)
            }
            is Result.Failure -> {
                showErrorGettingOrganizations.postValue(true)
            }
        }
    }

}
