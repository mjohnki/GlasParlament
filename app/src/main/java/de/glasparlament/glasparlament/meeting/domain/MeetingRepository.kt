package de.glasparlament.glasparlament.meeting.domain

import de.glasparlament.glasparlament.meeting.data.Meeting
import de.glasparlament.glasparlament.organization.data.BodyOrganization
import io.reactivex.Observable

interface MeetingRepository {
    fun  getMeetingList(url: String) : Observable<Meeting>
}