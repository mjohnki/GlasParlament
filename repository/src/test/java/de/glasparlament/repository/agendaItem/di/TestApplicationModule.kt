package de.glasparlament.repository.agendaItem.di

import dagger.Module
import dagger.Provides
import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.agendaItem.local.AgendaItemDb
import de.glasparlament.repository.agendaItem.local.AgendaItemFile
import de.glasparlament.repository.meeting.local.file.FileDb
import de.glasparlament.repository.meeting.local.meeting.MeetingDB
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestApplicationModule {

    @Provides
    @Singleton
    fun provideMeetingDao(): MeetingDao {
        val dao: MeetingDao = mockk()
        val meeting = MeetingDB(
                id = "1",
                name = "39. Sitzung des Plenums",
                body = "http://test.test"
        )

        coEvery { dao.getMeeting("http://meeting.test") } returns meeting

        return dao
    }

    @Provides
    @Singleton
    fun provideAgendaItemDao(): AgendaItemDao {
        val dao: AgendaItemDao = mockk()

        val agendaItem = AgendaItemDb(
                id = "123",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "14",
                meeting = "http://meeting.test"
        )
        val file = FileDb(
                id = "12",
                name = "new File",
                agendaItem = "http://agendaItem.test",
                accessUrl = "http://access.test"

        )
        val agendaItemFile = AgendaItemFile(
                agendaItem = agendaItem,
                files = listOf(file)
        )
        coEvery { dao.getAgendaItem(any()) } returns agendaItemFile
        coEvery { dao.getAgendaItems(any()) } returns listOf(agendaItemFile)
        coEvery { dao.searchtAgendaItems(any()) } returns listOf(agendaItemFile)

        return dao
    }
}