package com.example.loadout.model.lift.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.loadout.model.lift.LiftLog
import kotlinx.coroutines.flow.Flow

@Dao
interface LiftLogDao {

    @Insert
    suspend fun insert(liftLog: LiftLog): Long

    @Query("""
        SELECT * FROM lift_log
        WHERE sessionId = :sessionId
    """
    )
    fun getAllForSession(sessionId: Int): Flow<List<LiftLog>>


    @Update
    suspend fun update(liftLog: LiftLog)
}