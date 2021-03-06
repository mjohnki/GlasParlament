package de.glasparlament.repository.agendaItem.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agendaItem")
data class AgendaItemDb (
        @PrimaryKey
        @ColumnInfo(name = "agendaItemId")
        var id: String,
        @ColumnInfo(name = "number")
        var number: String,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "meeting")
        var meeting: String )
