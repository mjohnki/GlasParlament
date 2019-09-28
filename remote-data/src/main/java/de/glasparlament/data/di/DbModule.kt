package de.glasparlament.data.di

import android.app.Application
import androidx.room.Room
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.GPDatabase
import de.glasparlament.data.db.MeetingDao
import org.koin.dsl.module

val dbModule = module {
    single<MeetingDao> { provideMeetingDao(get()) }
    single<FileDao> { provideFileDao(get()) }
    single<AgendaItemDao> { provideAgendaItemDao(get()) }
    single<GPDatabase> { provideGPDatabase(get()) }
}

fun provideMeetingDao(database: GPDatabase) =
        database.meetingDao()

fun provideFileDao(database: GPDatabase) =
        database.fileDao()

fun provideAgendaItemDao(database: GPDatabase) =
        database.agendaItemDao()

fun provideGPDatabase(app : Application) =
        Room.databaseBuilder(
                app.applicationContext,
                GPDatabase::class.java,
                "Database").build()
