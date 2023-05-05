package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from asteroidentity")
    fun getAll(): LiveData<List<AsteroidEntity>>
    @Query("select * from asteroidentity where closeApproachDate==:today")
    fun getToday(today:String): LiveData<List<AsteroidEntity>>
    @Query("select * from asteroidentity where closeApproachDate>=:today")
    fun getWeek(today:String): LiveData<List<AsteroidEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids: List<AsteroidEntity>)
    @Query("DELETE FROM asteroidentity")
    fun deleteAll()
}

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDB : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AsteroidDB
        fun getDatabase(context: Context): AsteroidDB {
            synchronized(AsteroidDB::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDB::class.java,
                        "dataName"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
