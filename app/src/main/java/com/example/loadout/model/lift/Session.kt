package com.example.loadout.model.lift

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "session",
    foreignKeys = [ForeignKey(
        entity = TrainingBlock::class,
        parentColumns = ["id"],
        childColumns = ["trainingBlockId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("trainingBlockId")]
)
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainingBlockId: Int,
    val date: Instant,
    val weekNumber: Int
)
