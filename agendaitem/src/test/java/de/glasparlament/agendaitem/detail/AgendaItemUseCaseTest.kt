package de.glasparlament.agendaitem.detail

import de.glasparlament.agendaitem_repository.AgendaItem
import de.glasparlament.agendaitem_repository.AgendaItemRepository
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AgendaItemUseCaseTest {

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
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
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
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(agendaItem, (result as Transfer.Success).data)
    }
}