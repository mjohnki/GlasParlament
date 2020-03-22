package de.glasparlament.search

import de.glasparlament.repository.agendaItem.AgendaItemRepository
import de.glasparlament.search.useCase.SearchUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class SearchUseCaseTest {

    private val repository: AgendaItemRepository = mockk(relaxed = true)
    private val useCase = SearchUseCase(repository)

    @Test
    fun test_UseCase_works() {
        //given:
        val serach = "search"

        //when:
       runBlocking { useCase.execute(serach) }

        //then:
        coVerify { repository.searchAgendaItems(serach) }
    }
}