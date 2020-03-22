package de.glasparlament.repository.meeting.local.file

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file")
data class FileDb(
        @PrimaryKey
        @ColumnInfo(name = "fileId")
        var id: String,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "accessUrl")
        var accessUrl: String,
        @ColumnInfo(name = "agendaItem")
        var agendaItem: String)
