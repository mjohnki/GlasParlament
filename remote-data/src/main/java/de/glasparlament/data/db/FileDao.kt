package de.glasparlament.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.glasparlament.data.MeetingRemote

@Dao
interface FileDao {
    @Query("SELECT * FROM file")
    suspend fun getMeetings(): List<File>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(file: List<File>)
}