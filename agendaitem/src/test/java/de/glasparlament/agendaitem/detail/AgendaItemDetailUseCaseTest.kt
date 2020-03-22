package de.glasparlament.agendaitem.detail

import de.glasparlament.repository.agendaItem.AgendaItemRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class AgendaItemDetailUseCaseTest {

    private val repository = mockk<AgendaItemRepository>(relaxed = true)
    private val useCase  = AgendaItemUseCase(repository)

    @Test
    fun testUseCaseSuccess() {
        //given:
        val url = "http://test.test"

        //when:
        runBlocking {useCase.execute(url)}

        //then:
        coVerify { repository.getAgendaItem(url) }
    }
}