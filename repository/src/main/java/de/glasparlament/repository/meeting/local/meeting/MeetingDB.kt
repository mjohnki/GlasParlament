package de.glasparlament.repository.meeting.local.meeting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meeting")
data class MeetingDB(
        @PrimaryKey
        @ColumnInfo(name = "meetingId")
        var id: String = "",
        @ColumnInfo(name = "name")
        var name: String = "",
        @ColumnInfo(name = "body")
        var body: String = "")
