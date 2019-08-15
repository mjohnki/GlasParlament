package de.glasparlament.organization

class BodyOrganization constructor(
        var organizationId: String,
        var organizationName: String,
        var bodyId: String,
        var bodyName: String,
        var meeting: String,
        var bodyShortname: String){

    val name: String
        get() = "$bodyName · $organizationName"

    val shortname: String
        get() = "$bodyShortname · $organizationName"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BodyOrganization

        if (organizationId != other.organizationId) return false
        if (organizationName != other.organizationName) return false
        if (bodyId != other.bodyId) return false
        if (bodyName != other.bodyName) return false
        if (meeting != other.meeting) return false
        if (bodyShortname != other.bodyShortname) return false

        return true
    }

    override fun hashCode(): Int {
        var result = organizationId.hashCode()
        result = 31 * result + organizationName.hashCode()
        result = 31 * result + bodyId.hashCode()
        result = 31 * result + bodyName.hashCode()
        result = 31 * result + meeting.hashCode()
        result = 31 * result + bodyShortname.hashCode()
        return result
    }


}
