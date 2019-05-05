package de.glasparlament.glasparlament.organization.domain

import de.glasparlament.glasparlament.organization.data.BodyOrganization
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class OrganizationListUseCase(
        private val organizationRepository: OrganizationRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val data: List<BodyOrganization>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(): Observable<Result> {
        return organizationRepository.getBodyOrganizationList()
                .toList()
                .toObservable()
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }
}