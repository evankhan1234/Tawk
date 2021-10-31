package com.tawk.framework.mvvm.data.database.repository

import com.tawk.framework.mvvm.data.database.entities.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun insert(schedule: Schedule): Long

    suspend fun delete(schedule: Schedule): Int

     fun getAllSchedule(): Flow<List<Schedule>>

     suspend fun getScheduleByContentId(contentId: Long): Schedule?

     fun getScheduleByName(name: String):Flow<List<Schedule>>

    suspend fun updateScheduleByContentId(contentId: Long, notes: String): Int

    suspend fun deleteByContentId(contentId: Long)
}