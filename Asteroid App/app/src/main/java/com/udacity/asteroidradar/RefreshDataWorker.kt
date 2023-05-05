package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDB
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidDB.getDatabase(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.refreshData()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }

}
