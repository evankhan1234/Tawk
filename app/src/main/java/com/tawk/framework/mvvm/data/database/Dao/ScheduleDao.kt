package com.tawk.framework.mvvm.data.database.Dao

import androidx.room.*
import com.tawk.framework.mvvm.data.database.entities.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule): Long

    @Delete
    suspend fun delete(schedule: Schedule): Int

    @Query("SELECT * FROM Schedule")
    fun getAllSchedule(): Flow<List<Schedule>>

    @Query("Select * from Schedule where  login LIKE :name OR  note LIKE :name")
    fun getScheduleByName(name: String): Flow<List<Schedule>>

    @Query("SELECT * FROM Schedule WHERE id == :contentId")
    fun getScheduleByContentId(contentId: Long): Schedule?

    @Query("UPDATE Schedule SET note = :notes  WHERE id == :contentId")
    suspend fun updateScheduleByContentId(contentId: Long, notes: String): Int

    @Query("DELETE FROM Schedule WHERE id ==:contentId")
    suspend fun deleteByContentId(contentId: Long)
}