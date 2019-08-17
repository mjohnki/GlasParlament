package de.glasparlament.agendaitem.overview

import de.glasparlament.agendaitem.TestData
import de.glasparlament.data.Transfer
import de.glasparlament.meeting_repository.MeetingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AgendaItemListUseCaseTest {

    private val repository = mockk<MeetingRepository>()
    private val useCase = AgendaItemListUseCase(repository)

    @Test
    fun testUseCaseError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "Error Loading Data"
        val bodyList = Transfer.Error(errorMessage)
        coEvery { repository.getMeeting(url) } returns bodyList

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseSuccessSingle() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meeting_single_agendaitem)
        coEvery { repository.getMeeting(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(
                listOf(TestData.meeting_single_agendaitem.agendaItem[0]),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_16_17() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meeting_agendaitem_16_17)
        coEvery { repository.getMeeting(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(
                listOf(TestData.agendaItem_16, TestData.agendaItem_17),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_17_16() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meeting_agendaitem_17_16)
        coEvery { repository.getMeeting(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(
                listOf(TestData.agendaItem_16, TestData.agendaItem_17),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_17a_17b() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meeting_agendaitem_17a_17b)
        coEvery { repository.getMeeting(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(
                listOf(TestData.agendaItem_17a, TestData.agendaItem_17b),
                (result as Transfer.Success).data
        )
    }

    @Test
    fun testUseCaseSuccess_17a_17b_16_18() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meeting_agendaitem_17a_17b_16_18)
        coEvery { repository.getMeeting(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(
                listOf(TestData.agendaItem_16, TestData.agendaItem_17a, TestData.agendaItem_17b, TestData.agendaItem_18),
                (result as Transfer.Success).data
        )
    }
    @Test
    fun testUseCaseSuccess_7_4_6_5() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meeting_agendaitem_7_4_6_5)
        coEvery { repository.getMeeting(url) } returns data

        //when:
        val result = runBlocking { useCase.execute(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(
                listOf(TestData.agendaItem_4, TestData.agendaItem_5, TestData.agendaItem_6, TestData.agendaItem_7),
                (result as Transfer.Success).data
        )
    }
}