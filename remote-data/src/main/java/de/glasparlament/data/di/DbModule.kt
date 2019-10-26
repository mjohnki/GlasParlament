package de.glasparlament.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import de.glasparlament.data.db.GPDatabase
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideMeetingDao(database: GPDatabase) =
            database.meetingDao()

    @Provides
    @Singleton
    fun provideFileDao(database: GPDatabase) =
            database.fileDao()

    @Provides
    @Singleton
    fun provideAgendaItemDao(database: GPDatabase) =
            database.agendaItemDao()

    @Provides
    @Singleton
    fun provideGPDatabase(app : Application) =
            Room.databaseBuilder(
                    app.applicationContext,
                    GPDatabase::class.java,
                    "Database").build()
}
