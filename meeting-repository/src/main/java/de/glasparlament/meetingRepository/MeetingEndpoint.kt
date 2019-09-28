package de.glasparlament.meetingRepository

import de.glasparlament.data.MeetingList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MeetingEndpoint {

    @GET
    suspend fun getMeetingList(@Url url:String): Response<MeetingList>
}
