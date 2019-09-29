package de.glasparlament.organization

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class OrganizationAdapterTest {

    private val diff = DiffCallback()

    @Test
    fun testItemsSameWorksWithSameObjects(){
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
    fun testItemsSameWorksWithNotSameObjects(){
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
    fun testContentsSameWorksWithSameObjects(){
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
    fun testContentsSameWorksWithNotSameObjects(){
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
}