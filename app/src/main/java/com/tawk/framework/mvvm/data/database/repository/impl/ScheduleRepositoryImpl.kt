package com.tawk.framework.mvvm.data.database.repository.impl

import com.tawk.framework.mvvm.data.database.Dao.ScheduleDao
import com.tawk.framework.mvvm.data.database.entities.Schedule
import com.tawk.framework.mvvm.data.database.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow


class ScheduleRepositoryImpl (private val dao: ScheduleDao):
    ScheduleRepository {
    override suspend fun insert(schedule: Schedule): Long {
        return dao.insert(schedule)
    }

    override suspend fun delete(schedule: Schedule): Int {
        return dao.delete(schedule)
    }

    override  fun getAllSchedule(): Flow<List<Schedule>> {
        return dao.getAllSchedule()
    }

    override suspend fun getScheduleByContentId(contentId: Long): Schedule?  {
        return dao.getScheduleByContentId(contentId)
    }

    override  fun getScheduleByName(name: String): Flow<List<Schedule>> {
        return dao.getScheduleByName(name)
    }

    override suspend fun updateScheduleByContentId(
        contentId: Long, notes: String
    ): Int {
        return dao.updateScheduleByContentId(contentId,notes)
    }

    override suspend fun deleteByContentId(contentId: Long) {
        return dao.deleteByContentId(contentId)
    }
}