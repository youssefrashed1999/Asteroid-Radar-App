package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDB
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidsRepository(val database: AsteroidDB) {
    val weekAsteroids : LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getWeek(getTodayDate())){
        it.asDomainModel()
    }
    val todayAsteroids : LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getToday(getTodayDate())){
        it.asDomainModel()
    }
    val allAsteroids : LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAll()){
        it.asDomainModel()
    }
    suspend fun refreshData (){
        withContext(Dispatchers.IO){
            val response = Api.retrofitService.getAsteroids()
            val jsonObject = JSONObject(response)
            val data = parseAsteroidsJsonResult(jsonObject)
            database.asteroidDao.insert(data.asDatabaseModel())
        }

    }
    fun getTodayDate():String{
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)
        return current
    }
    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = Api.retrofitService.getPictureOfDay("DEMO_KEY")
        }
        return pictureOfDay
    }


}