package de.glasparlament.repository.agendaItem.local

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface AgendaItemDao {

    @Transaction
    @Query("SELECT * FROM agendaItem WHERE meeting = :param")
    suspend fun getAgendaItems(param: String): List<AgendaItemFile>

    @Transaction
    @Query("SELECT * FROM agendaItem WHERE agendaItemId = :id")
    suspend fun getAgendaItem(id: String): AgendaItemFile

    @Transaction
    @Query("SELECT * FROM agendaItem WHERE name LIKE :search ORDER BY meeting DESC")
    suspend fun searchtAgendaItems(search: String): List<AgendaItemFile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agendaItem: AgendaItemDb)
}
