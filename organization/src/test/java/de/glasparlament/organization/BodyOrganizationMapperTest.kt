package de.glasparlament.organization

import org.junit.Assert
import org.junit.Test

class BodyOrganizationMapperTest {

    @Test
    fun testMapper() {
        //when:
        val result = BodyOrganizationMapper.map(TestData.body, TestData.organization)

        //then:
        Assert.assertEquals(result, BodyOrganization(
                organizationId = TestData.organization.id,
                organizationName = TestData.organization.name,
                bodyId = TestData.body.id,
                bodyName = TestData.body.name,
                meeting = TestData.organization.meeting,
                bodyShortname = TestData.body.shortname
        ))
    }

}