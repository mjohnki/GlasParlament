package de.glasparlament.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import de.glasparlament.data.MeetingRemote

@Database(entities = [Meeting::class, AgendaItem::class, File::class], version = 1)
public abstract class GPDatabase : RoomDatabase() {

    abstract fun meetingDao(): MeetingDao

    abstract fun agendaItemDao(): AgendaItemDao

    abstract fun fileDao(): FileDao
}
