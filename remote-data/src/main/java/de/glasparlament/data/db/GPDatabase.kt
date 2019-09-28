package de.glasparlament.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meeting::class, AgendaItem::class, File::class], version = 1)
abstract class GPDatabase : RoomDatabase() {

    abstract fun meetingDao(): MeetingDao

    abstract fun agendaItemDao(): AgendaItemDao

    abstract fun fileDao(): FileDao
}
