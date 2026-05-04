package com.example.loadout.model.lift.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.loadout.model.lift.LiftLog
import com.example.loadout.model.lift.OneRepMax
import com.example.loadout.model.lift.Session
import com.example.loadout.model.lift.TrainingBlock
import com.example.loadout.model.lift.db.dao.LiftLogDao
import com.example.loadout.model.lift.db.dao.OneRepMaxDao
import com.example.loadout.model.lift.db.dao.SessionDao
import com.example.loadout.model.lift.db.dao.TrainingBlockDao

@TypeConverters(Converters::class)
@Database(
    entities = [
        TrainingBlock::class,
        Session::class,
        OneRepMax::class,
        LiftLog::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trainingBlockDao(): TrainingBlockDao
    abstract fun oneRepMaxDao(): OneRepMaxDao
    abstract fun sessionDao(): SessionDao
    abstract fun liftLogDao(): LiftLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "loadout.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}