package de.glasparlament.organization

import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import com.idanatz.oneadapter.internal.holders.ViewBinder
import io.mockk.every
import io.mockk.verify

class OrganizationItemClickEventTest {

    private val viewModel = mockk<OrganizationListViewModel>()
    private val clickEvent  = OrganizationItemClickEvent(viewModel)

    @Test
    fun testItemClickEvent() {
        //given:
        val viewBinder =  mockk<ViewBinder>()
        val model = TestData.bodyOrganization
        every { viewModel.navigate(any()) } returns Unit

        //when:
        clickEvent.onClick(model, viewBinder)

        //then:
        verify { viewModel.navigate(any()) }
    }

}