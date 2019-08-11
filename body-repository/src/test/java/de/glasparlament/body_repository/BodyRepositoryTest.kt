package de.glasparlament.body_repository

import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class BodyRepositoryTest {

    private val api = mockk<BodyApi>()
    private val repository = BodyRepositoryImpl(api)

    @Test
    fun testGetBodyListWithError() {
        //given:
        val errorMessage = "error loading data"
        val response = Transfer.Error(errorMessage)
        coEvery { api.getBodyList() } returns response

        //when:
        val result = runBlocking {repository.getBodyList()}

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetBodyListWithSuccess() {
        //given:
        val response = Transfer.Success(TestData.bodyList)
        coEvery { api.getBodyList() } returns response

        //when:
        val result = runBlocking {repository.getBodyList()}

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.bodyList, (result as Transfer.Success).data)
    }
}