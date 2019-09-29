package de.glasparlament.organization

data class BodyOrganization constructor(
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
}
