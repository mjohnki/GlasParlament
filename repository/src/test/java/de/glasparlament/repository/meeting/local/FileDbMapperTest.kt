package de.glasparlament.repository.meeting.local

import de.glasparlament.repository.meeting.local.file.File
import de.glasparlament.repository.meeting.local.file.FileMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FileDbMapperTest {

    @Test
    fun test_map_should_work_with_some_items(){
        //given:
        val file = File(
                id = ID,
                name = NAME,
                accessUrl = ACCESS_URL
        )
        val files = listOf(file)

        //when:
        val result = FileMapper.map(files, AGENDA_ITEM_ID)

        //then:
        assertEquals(files.size, result.size)
        assertEquals(ID, result[0].id)
        assertEquals(NAME, result[0].name)
        assertEquals(ACCESS_URL, result[0].accessUrl)
        assertEquals(AGENDA_ITEM_ID, result[0].agendaItem)
    }

    @Test
    fun test_map_should_work_without_some_items(){

        //when:
        val result = FileMapper.map(emptyList(), AGENDA_ITEM_ID)

        //then:
        assertEquals(0, result.size)
    }

    companion object {
        private const val ID = "file.id"
        private const val NAME = "file.name"
        private const val ACCESS_URL = "file.accessUrl"
        private const val AGENDA_ITEM_ID = "agendaItemId"
    }

}