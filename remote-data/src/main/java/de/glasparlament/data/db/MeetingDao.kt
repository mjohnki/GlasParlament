package de.glasparlament.data.db

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MeetingDao {

    @Transaction
    @Query("SELECT * FROM Meeting")
    suspend fun getMeetings(): List<MeetingAgendaItem>

    @Transaction
    @Query("SELECT * from Meeting WHERE meetingId = :id")
    suspend fun  getMeeting(id: String): Meeting

    @Query("SELECT COUNT(*) from Meeting WHERE meetingId = :id")
    suspend fun  hasMeeting(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meetings: Meeting)
}
