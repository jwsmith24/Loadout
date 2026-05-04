package com.example.loadout.model.lift

import com.example.loadout.model.lift.db.dao.LiftLogDao
import com.example.loadout.model.lift.db.dao.OneRepMaxDao
import com.example.loadout.model.lift.db.dao.SessionDao
import com.example.loadout.model.lift.db.dao.TrainingBlockDao
import kotlinx.coroutines.flow.Flow

class LiftingRepository(
    private val trainingBlockDao: TrainingBlockDao,
    private val oneRepMaxDao: OneRepMaxDao,
    private val sessionDao: SessionDao,
    private val liftLogDao: LiftLogDao
) {

    // Training Blocks
    suspend fun insertBlock(trainingBlock: TrainingBlock) = trainingBlockDao.insert(trainingBlock)
    fun getActiveBlock(): Flow<TrainingBlock?> = trainingBlockDao.getLatest()
    fun getAllTrainingBlocks(): Flow<List<TrainingBlock>> = trainingBlockDao.getAll()

    // Sessions
    suspend fun insertSession(session: Session) = sessionDao.insert(session)
    fun getSessionsForTrainingBlock(trainingBlockId: Int) = sessionDao.getAllForTrainingBlock(trainingBlockId)

    // One Rep Max
    suspend fun insertOneRepMax(oneRepMax: OneRepMax) = oneRepMaxDao.insert(oneRepMax)
    fun getLatestOneRepMaxForExercise(exercise: Exercise) = oneRepMaxDao.getLatestForExercise(exercise)
    fun getOneRepMaxHistoryForExercise(exercise: Exercise) = oneRepMaxDao.getHistoryForExercise(exercise)
    fun getLifetimeOneRepMaxForExercise(exercise: Exercise) = oneRepMaxDao.getLifetimeMaxForExercise(exercise)

    // Lift Log
    suspend fun insertLiftLog(liftLog: LiftLog) = liftLogDao.insert(liftLog)
    suspend fun updateLog(liftLog: LiftLog) = liftLogDao.update(liftLog)
    fun getAllLogsForSession(sessionId: Int) = liftLogDao.getAllForSession(sessionId)


}