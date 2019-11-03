package de.glasparlament.search

import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.data.Transfer
import de.glasparlament.search.useCase.SearchUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemSearchUseCaseTest {

    private val repository = mockk<AgendaItemRepository>()
    private val useCase  = SearchUseCase(repository)

    @Test
    fun testUseCaseError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "Error Loading Data"
        val bodyList = Transfer.Error(errorMessage)
        coEvery { repository.searchAgendaItems(url) } returns  bodyList

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")
        val url = "http://test.test"
        val data = Transfer.Success(listOf(agendaItem))
        coEvery { repository.searchAgendaItems(url) } returns  data

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(listOf(agendaItem), (result as Transfer.Success).data)
    }
}