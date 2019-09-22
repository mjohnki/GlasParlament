package de.glasparlament.agendaItemRepository

import de.glasparlament.data.db.File
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileMapperTest {
    val mapper = FileMapper

    @Test
    fun test_map_should_work(){
        //given:
        val file = File(
                id = "123",
                name = "new file",
                accessUrl = "http://access.test",
                agendaItem = "http://agenda.test"
        )

        //when:
        val result = mapper.map(file)

        //then:
        Assertions.assertEquals(file.id, result.id)
        Assertions.assertEquals(file.name, result.name)
        Assertions.assertEquals(file.accessUrl, result.accessUrl)
    }
}