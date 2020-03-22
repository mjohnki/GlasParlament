package de.glasparlament.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import de.glasparlament.repository.agendaItem.local.AgendaItemDb
import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.meeting.local.file.FileDb
import de.glasparlament.repository.meeting.local.file.FileDao
import de.glasparlament.repository.meeting.local.meeting.MeetingDB
import de.glasparlament.repository.meeting.local.meeting.MeetingDao

@Database(entities = [MeetingDB::class, AgendaItemDb::class, FileDb::class], version = 1)
abstract class GPDatabase : RoomDatabase() {

    abstract fun meetingDao(): MeetingDao

    abstract fun agendaItemDao(): AgendaItemDao

    abstract fun fileDao(): FileDao
}
