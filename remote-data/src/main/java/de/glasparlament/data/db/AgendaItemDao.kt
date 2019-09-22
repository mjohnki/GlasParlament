package de.glasparlament.data.db

import androidx.room.*
import de.glasparlament.data.MeetingRemote

@Dao
interface AgendaItemDao {

    @Transaction
    @Query("SELECT * FROM agendaItem WHERE meeting = :param")
    suspend fun getAgendaItems(param: String): List<AgendaItemFile>

    @Transaction
    @Query("SELECT * FROM agendaItem WHERE agendaItemId = :id")
    suspend fun getAgendaItem(id: String): AgendaItemFile

    @Transaction
    @Query("SELECT * FROM agendaItem WHERE name LIKE :search ORDER BY meeting")
    suspend fun searchtAgendaItems(search: String): List<AgendaItemFile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agendaItem: AgendaItem)
}