package de.glasparlament.glasparlament.meeting.data

import io.reactivex.Observable
import retrofit2.Response

class MeetingApi(private val endpoint: MeetingEndpoint) {

    fun getMeetingList(url: String): Observable<Meeting> {

        return endpoint.getMeetingList(url)
                .map(this::handleListResponse)
                .toObservable()
                .flatMapIterable { t: List<Meeting> -> t }
    }

    fun getMeeting(url: String): Observable<Meeting> {

        return endpoint.getMeeting(url)
                .map(this::handleListResponse)
                .toObservable()
    }

    private fun handleListResponse(response: Response<MeetingList>): List<Meeting> {
        if (response.isSuccessful) {
            return response.body()!!.data
        } else {
            return emptyList()
        }
    }

    private fun handleListResponse(response: Response<Meeting>): Meeting {
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            return Meeting()
        }
    }
}