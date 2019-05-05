package de.glasparlament.glasparlament.meeting.domain

import de.glasparlament.glasparlament.meeting.data.Meeting
import de.glasparlament.glasparlament.organization.data.BodyOrganization
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MeetingListUseCase(
        private val repository: MeetingRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val data: List<Meeting>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(url: String): Observable<Result> {
        return repository.getMeetingList(url)
                .toSortedList { m1, m2 ->  m2.name.compareTo(m1.name) }
                .toObservable()
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }
}