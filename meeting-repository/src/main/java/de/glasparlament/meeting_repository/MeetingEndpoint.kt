package de.glasparlament.meeting_repository

import de.glasparlament.data.MeetingRemote
import de.glasparlament.data.MeetingList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MeetingEndpoint {

    @GET
    suspend fun getMeetingList(@Url url:String): Response<MeetingList>

    @GET
    suspend fun getMeeting(@Url url:String): Response<MeetingRemote>
}
