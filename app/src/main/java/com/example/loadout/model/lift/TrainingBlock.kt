package com.example.loadout.model.lift

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "training_block")
data class TrainingBlock(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startDate: Instant
)