package de.glasparlament.organization

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.synthetic.main.organization_list_item.view.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class OrganizationAdapterTest {

    private val diff = DiffCallback()
    private val view = mockk<View>()
    private val resources = mockk<Resources>(relaxed = true)
    private val organizationName = mockk<TextView>(relaxed = true)
    private val organizationItem = mockk<MaterialCardView>(relaxed = true)
    private val viewHolder = OrganizationViewHolder(view)
    private val listener = mockk<View.OnClickListener>()

    @Test
    fun test_itemsSame_works_with_same_objects() {
        //given:
        val data = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test.test",
                bodyShortname = "AGH"
        )

        //when:
        val result = diff.areItemsTheSame(data, data)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_itemsSame_works_with_not_same_objects() {
        //given:
        val data = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test.test",
                bodyShortname = "AGH"
        )
        val dataOther = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test.test",
                bodyShortname = "AGH"
        )

        //when:
        val result = diff.areItemsTheSame(data, dataOther)

        //then:
        assertFalse(result)
    }

    @Test
    fun test_contentsSame_works_with_same_Objects() {
        //given:
        val data = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test.test",
                bodyShortname = "AGH"
        )

        //when:
        val result = diff.areContentsTheSame(data, data)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_contentsSame_works_with_different_objects() {
        //given:
        val data = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test.test",
                bodyShortname = "AGH"
        )
        val dataOther = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test2.test",
                bodyShortname = "AGH"
        )

        //when:
        val result = diff.areContentsTheSame(data, dataOther)

        //then:
        assertFalse(result)
    }

    @Test
    fun test_bind_works() {
        //given:
        val name = "AGH Plenum"
        every { resources.getString(any(), any(), any()) } returns name
        every { view.resources } returns resources
        every { view.organizationName } returns organizationName
        every { view.organizationItem } returns organizationItem
        val data = BodyOrganization(
                organizationId = "organizationId",
                organizationName = "Plenum",
                bodyId = "bodyId",
                bodyName = "Abgeordnetenhaus von Berlin",
                meeting = "http://test.test",
                bodyShortname = "AGH"
        )

        //when:
        viewHolder.bind(data, listener)

        //then:
        verify { resources.getString(any(), data.bodyName, data.organizationName) }
        verify { organizationName.text = name }
        verify { organizationItem.setOnClickListener(listener) }

    }
}