package de.glasparlament.agendaitem_repository

import de.glasparlament.data.db.File
import org.junit.Assert
import org.junit.Test

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
        Assert.assertEquals(file.id, result.id)
        Assert.assertEquals(file.name, result.name)
        Assert.assertEquals(file.accessUrl, result.accessUrl)
    }
}