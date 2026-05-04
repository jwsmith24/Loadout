package com.example.loadout.model.lift

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "one_rep_max")
data class OneRepMax(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exercise: Exercise,
    val weight: Double,
    val recordedAt: Instant
)
