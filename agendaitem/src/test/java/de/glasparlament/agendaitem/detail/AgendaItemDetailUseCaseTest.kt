package de.glasparlament.agendaitem.detail

import de.glasparlament.data.Transfer
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemDetailUseCaseTest {

    private val repository = mockk<AgendaItemRepository>()
    private val useCase  = AgendaItemUseCase(repository)

    @Test
    fun testUseCaseError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "Error Loading Data"
        val bodyList = Transfer.Error(errorMessage)
        coEvery { repository.getAgendaItem(url) } returns  bodyList

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val url = "http://test.test"
        val data = Transfer.Success(agendaItem)
        coEvery { repository.getAgendaItem(url) } returns  data

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(agendaItem, (result as Transfer.Success).data)
    }
}