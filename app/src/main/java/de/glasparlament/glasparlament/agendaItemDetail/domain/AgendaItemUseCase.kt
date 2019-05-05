package de.glasparlament.glasparlament.agendaItemDetail.domain

import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import io.reactivex.Observable

class AgendaItemUseCase(
        private val repository: AgendaItemDetailRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val data: AgendaItem) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(url: String): Observable<Result> {
        return repository.getAgendaItem(url)
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }
}