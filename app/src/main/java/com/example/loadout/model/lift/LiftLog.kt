package com.example.loadout.model.lift

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "lift_log",
    foreignKeys = [ForeignKey(
        entity = Session::class,
        parentColumns = ["id"],
        childColumns = ["sessionId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("sessionId")])
data class LiftLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exercise: Exercise,
    val sessionId: Int,
    val targetWeight: Double,
    val targetReps: Int,
    val actualWeight: Double? = null,
    val actualReps: Int? = null,
    val setsCompleted: Int? = null

)