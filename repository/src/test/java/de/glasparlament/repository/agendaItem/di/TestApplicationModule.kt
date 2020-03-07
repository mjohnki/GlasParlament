package de.glasparlament.repository.agendaItem.di

import dagger.Module
import dagger.Provides
import de.glasparlament.data.db.*
import io.mockk.coEvery
import io.mockk.mockk

@Module
class TestApplicationModule {

    @Provides
    fun provideMeetingDao(): MeetingDao {
        val dao: MeetingDao = mockk()
        val meeting = Meeting(
                id = "1",
                name = "39. Sitzung des Plenums",
                body = "http://test.test"
        )

        coEvery { dao.getMeeting("http://meeting.test") } returns meeting

        return dao
    }

    @Provides
    fun provideAgendaItemDao(): AgendaItemDao {
        val dao: AgendaItemDao = mockk()

        val agendaItem = AgendaItem(
                id = "123",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "14",
                meeting = "http://meeting.test"
        )
        val file = File(
                id = "12",
                name = "new File",
                agendaItem = "http://agendaItem.test",
                accessUrl = "http://access.test"

        )
        val agendaItemFile = AgendaItemFile(
                agendaItem = agendaItem,
                files = listOf(file)
        )
        coEvery { dao.getAgendaItem("http://test.test") } returns agendaItemFile
        coEvery { dao.getAgendaItems("http://test.test") } returns listOf(agendaItemFile)
        coEvery { dao.searchtAgendaItems("%holz%") } returns listOf(agendaItemFile)

        return dao
    }
}