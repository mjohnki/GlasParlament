package de.glasparlament.data

class File constructor(
       var id: String,
        var name: String,
        var accessUrl: String){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (id != other.id) return false
        if (name != other.name) return false
        if (accessUrl != other.accessUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + accessUrl.hashCode()
        return result
    }
}

