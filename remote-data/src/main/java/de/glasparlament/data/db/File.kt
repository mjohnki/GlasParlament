package de.glasparlament.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file")
data class File(
        @PrimaryKey
        @ColumnInfo(name = "fileId")
        var id: String,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "accessUrl")
        var accessUrl: String,
        @ColumnInfo(name = "agendaItem")
        var agendaItem: String) {

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

