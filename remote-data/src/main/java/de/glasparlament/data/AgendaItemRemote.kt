package de.glasparlament.data

class AgendaItemRemote constructor(
        var id: String,
        var number: String,
        var name: String,
        var meeting: String,
        var auxiliaryFile: List<FileRemote> ){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AgendaItemRemote

        if (id != other.id) return false
        if (number != other.number) return false
        if (name != other.name) return false
        if (meeting != other.meeting) return false
        if (auxiliaryFile != other.auxiliaryFile) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + meeting.hashCode()
        result = 31 * result + auxiliaryFile.hashCode()
        return result
    }
}
