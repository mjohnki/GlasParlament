package de.glasparlament.glasparlament.agendaItemOverview.domain

import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import io.reactivex.Observable

class AgendaItemListUseCase(
        private val repository: AgendaItemRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val data: List<AgendaItem>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(url: String): Observable<Result> {
        return repository.getAgendaItemList(url)
                .toSortedList(sort())
                .toObservable()
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }

    private fun sort(): Comparator<AgendaItem> {
        return Comparator { a1: AgendaItem, a2: AgendaItem ->
            prepareCompareValue(a1.number).compareTo(prepareCompareValue(a2.number))
        }
    }

    private fun prepareCompareValue(name: String) : String {
        var value = name
        if(value.indexOf(".")  != -1){
            value = value.substring(0,value.indexOf("."))
        }
        if(value.length == 2){
            return value
        }
        return "0".plus(value)
    }
}