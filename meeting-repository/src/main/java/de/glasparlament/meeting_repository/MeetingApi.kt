package de.glasparlament.meeting_repository

import de.glasparlament.data.BaseApi
import de.glasparlament.data.Transfer
import de.glasparlament.data.MeetingRemote
import de.glasparlament.data.MeetingList

class MeetingApi(private val endpoint: MeetingEndpoint) : BaseApi() {

    suspend fun getMeetingList(url: String): Transfer<MeetingList> {
        return safeApiCall(
                call = { endpoint.getMeetingList(url) },
                errorMessage = errorMessageMeetingList
        )
    }

    suspend fun getMeeting(url: String): Transfer<MeetingRemote> {
        return safeApiCall(
                call = { endpoint.getMeeting(url) },
                errorMessage = errorMessageMeeting
        )
    }

    companion object{
        const val errorMessageMeetingList = "Error Fetching MeetingList"
        const val errorMessageMeeting = "Error Fetching Meeting"
    }
}