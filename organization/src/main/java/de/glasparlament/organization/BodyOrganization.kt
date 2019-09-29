package de.glasparlament.organization

data class BodyOrganization(
        val organizationId: String,
        val organizationName: String,
        val bodyId: String,
        val bodyName: String,
        val meeting: String,
        val bodyShortname: String) {

    val name: String
        get() = "$bodyName · $organizationName"

    val shortname: String
        get() = "$bodyShortname · $organizationName"
}
