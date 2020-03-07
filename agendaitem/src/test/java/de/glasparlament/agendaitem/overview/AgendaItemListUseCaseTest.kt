package de.glasparlament.agendaitem.overview

import de.glasparlament.data.Transfer
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemListUseCaseTest {

    private val repository = mockk<AgendaItemRepository>()
    private val useCase = AgendaItemListUseCase(repository)

    @Test
    fun testUseCaseError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "Error Loading Data"
        val bodyList = Transfer.Error(errorMessage)
        coEvery { repository.getAgendaItems(url) } returns bodyList

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseSuccessSingle() {
        //given:
        val url = "http://test.test"
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem))
        coEvery { repository.getAgendaItems(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(
                listOf(agendaItem),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_16_17() {
        //given:
        val url = "http://test.test"
        val agendaItem17 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem16 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem16, agendaItem17))
        coEvery { repository.getAgendaItems(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(
                listOf(agendaItem16, agendaItem17),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_17_16() {
        //given:
        val url = "http://test.test"
        val agendaItem17 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem16 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem17, agendaItem16))
        coEvery { repository.getAgendaItems(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(
                listOf(agendaItem16, agendaItem17),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_17a_17b() {
        //given:
        val url = "http://test.test"
        val agendaItem17a = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17.a",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem17b = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17.b",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem17a, agendaItem17b))
        coEvery { repository.getAgendaItems(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(
                listOf(agendaItem17a, agendaItem17b),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_17a_17b_16_18() {
        //given:
        val url = "http://test.test"
        val agendaItem17a = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17.a",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem17b = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17.b",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem16 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem18 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "18",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem17a, agendaItem17b, agendaItem16, agendaItem18))
        coEvery { repository.getAgendaItems(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(
                listOf(agendaItem16, agendaItem17a, agendaItem17b, agendaItem18),
                (result as Transfer.Success).data
        )
    }
    @Test
    fun testUseCaseSuccess_7_4_6_5() {
        //given:
        val url = "http://test.test"
        val agendaItem4 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "4",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem5 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "5",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem6 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "6",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItem7 = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "7",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val data = Transfer.Success(listOf(agendaItem7, agendaItem4, agendaItem6, agendaItem5))
        coEvery { repository.getAgendaItems(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(
                listOf(agendaItem4, agendaItem5, agendaItem6, agendaItem7),
                (result as Transfer.Success).data
        )
    }
}