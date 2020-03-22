package de.glasparlament.repository.meeting.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface MeetingEndpoint {

    @GET
    suspend fun getMeetingList(@Url url:String): MeetingList
}
