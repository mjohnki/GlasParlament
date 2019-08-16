package de.glasparlament.organization

import android.widget.TextView
import com.idanatz.oneadapter.internal.holders.ViewBinder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OrganizationItemModuleTest {

    private val itemModule  = OrganizationItemModule()

    @Test
    fun testMapper() {
        //given:
        val organizationName =  mockk<TextView>()
        val viewBinder =  mockk<ViewBinder>()
        every { viewBinder.findViewById<TextView>(R.id.organizationName) } returns organizationName
        val model = TestData.bodyOrganization
        every {organizationName.setText(eq(model.name)) } returns Unit

        //when:
        itemModule.onBind(model, viewBinder)

        //then:
        verify { organizationName.setText(model.name) }
    }

}