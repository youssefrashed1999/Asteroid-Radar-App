package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDB
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var database = AsteroidDB.getDatabase(application)
    private val repository = AsteroidsRepository(database)
    private var _allAsteroids = repository.allAsteroids
    val allAsteroids:LiveData<List<Asteroid>>
    get() = _allAsteroids
    private var _todayAsteroids = repository.todayAsteroids
    val todayAsteroids:LiveData<List<Asteroid>>
        get() = _todayAsteroids
    private var _weekAsteroids = repository.weekAsteroids
    val weekAsteroids:LiveData<List<Asteroid>>
        get() = _weekAsteroids
    private val _adapter=MutableLiveData<Int>()
    val adapter:LiveData<Int>
    get() = _adapter

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    init {
        _adapter.value=1
        refreshAsteroids()
        getPictureOfDay()
    }


    private fun refreshAsteroids() {
        viewModelScope.launch {
            try {
                repository.refreshData()

            } catch (e: Exception) {
            }
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = repository.getPictureOfDay()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

//    fun showToday(){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                val today=getTodayDate()
//                _asteroids=Transformations.map(database.asteroidDao.getToday(today)){
//                    it.asDomainModel()
//                }
//            }
//        }
//    }
//
//    fun showWeek(){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                val today=getTodayDate()
//                _asteroids=Transformations.map(database.asteroidDao.getWeek(today)){
//                    it.asDomainModel()
//                } as MutableLiveData<List<Asteroid>>
//            }
//        }
//
//    }
//
//    fun showAll(){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                _asteroids=Transformations.map(database.asteroidDao.getAll()){
//                    it.asDomainModel()
//                } as MutableLiveData<List<Asteroid>>
//            }
//        }
//    }
//    fun getTodayDate():String{
//        val time = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("yyyy-MM-dd")
//        val current = formatter.format(time)
//        return current
//    }
    fun updateAdapter(x:Int){
        _adapter.value=x
    }

}
