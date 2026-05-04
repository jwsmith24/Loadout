package com.example.loadout.model.lift.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loadout.model.lift.Exercise
import com.example.loadout.model.lift.OneRepMax
import kotlinx.coroutines.flow.Flow

@Dao
interface OneRepMaxDao {

    @Insert
    suspend fun insert(oneRepMax: OneRepMax): Long

    // for block generation: what is my current 1RM for this lift?
    @Query("""
        SELECT * FROM one_rep_max
        WHERE exercise = :exercise
        ORDER BY recordedAt DESC
        LIMIT 1
    """)
    fun getLatestForExercise(exercise: Exercise): Flow<OneRepMax?>

    // for showing progress history on a given lift
    @Query("""
        SELECT * FROM one_rep_max
        WHERE exercise = :exercise
        ORDER BY recordedAt DESC
    """)
    fun getHistoryForExercise(exercise: Exercise): Flow<List<OneRepMax>>

    // what is the best 1RM I've ever had for this lift?
    @Query("""
        SELECT * FROM one_rep_max
        WHERE exercise = :exercise
        ORDER BY weight DESC
        LIMIT 1
    """)
    fun getLifetimeMaxForExercise(exercise: Exercise): Flow<OneRepMax?>

}