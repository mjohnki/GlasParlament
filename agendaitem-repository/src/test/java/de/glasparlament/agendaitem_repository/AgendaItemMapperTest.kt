package de.glasparlament.agendaitem_repository

import de.glasparlament.data.db.AgendaItem
import de.glasparlament.data.db.AgendaItemFile
import de.glasparlament.data.db.File
import org.junit.Assert
import org.junit.Test

class AgendaItemMapperTest {

    private val mapper = AgendaItemMapper

    @Test
    fun test_AgendaItemMapper_should_work() {
        //given:
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

        //when:
        val result = mapper.map(agendaItemFile)

        //then:
        Assert.assertEquals(agendaItem.id, result.id)
        Assert.assertEquals(agendaItem.name, result.name)
        Assert.assertEquals(agendaItem.number, result.number)
        Assert.assertEquals(agendaItem.meeting, result.meeting)
        Assert.assertEquals(1, result.auxiliaryFile.size)
        Assert.assertEquals(file.id, result.auxiliaryFile[0].id)
    }
}