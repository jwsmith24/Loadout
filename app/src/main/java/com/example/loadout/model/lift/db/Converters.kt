package com.example.loadout.model.lift.db

import androidx.room.TypeConverter
import com.example.loadout.model.lift.Exercise
import java.time.Instant

class Converters {

    @TypeConverter
    fun longToInstant(long: Long) = Instant.ofEpochMilli(long)

    @TypeConverter
    fun instantToLong(instant: Instant) = instant.toEpochMilli()

    @TypeConverter
    fun exerciseToString(exercise: Exercise) = exercise.name

    @TypeConverter
    fun stringToExercise(string: String) = Exercise.valueOf(string)
}

