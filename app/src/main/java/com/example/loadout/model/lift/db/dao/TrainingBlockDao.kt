package com.example.loadout.model.lift.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loadout.model.lift.TrainingBlock
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingBlockDao {

    @Insert
    suspend fun insert(trainingBlock: TrainingBlock): Long

    @Query("SELECT * FROM training_block")
    fun getAll(): Flow<List<TrainingBlock>>

    @Query("""
        SELECT * FROM training_block
        WHERE id = :id
    """)
    fun getById(id: Int): Flow<TrainingBlock?>
}