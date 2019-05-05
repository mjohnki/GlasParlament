package de.glasparlament.glasparlament.meeting.data

import de.glasparlament.glasparlament.meeting.domain.MeetingRepository
import io.reactivex.Observable

class MeetingRepositoryImpl(private val api: MeetingApi) : MeetingRepository {

    override fun getMeetingList(url: String): Observable<Meeting> {
        return api.getMeetingList(url)
    }
}

