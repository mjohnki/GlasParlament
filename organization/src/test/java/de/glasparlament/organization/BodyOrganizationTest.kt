package de.glasparlament.organization

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BodyOrganizationTest {

    @Test
    fun test_name_works() {
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
        val name = data.name

        //then:
        Assertions.assertEquals("Abgeordnetenhaus von Berlin · Plenum", name)
    }

    @Test
    fun test_shortname_works() {
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
        val name = data.shortname

        //then:
        Assertions.assertEquals("AGH · Plenum", name)
    }
}