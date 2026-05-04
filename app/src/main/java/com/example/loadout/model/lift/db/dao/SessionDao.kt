package com.example.loadout.model.lift.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loadout.model.lift.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun insert(session: Session): Long

    @Query("""
        SELECT * FROM session
        WHERE trainingBlockId = :trainingBlockId
    """)
    fun getAllForTrainingBlock(trainingBlockId: Int): Flow<List<Session>>
}