package de.glasparlament.glasparlament.meeting.data

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MeetingEndpoint {

    @GET
    fun getMeetingList(@Url url:String): Single<Response<MeetingList>>

    @GET
    fun getMeeting(@Url url:String): Single<Response<Meeting>>
}
