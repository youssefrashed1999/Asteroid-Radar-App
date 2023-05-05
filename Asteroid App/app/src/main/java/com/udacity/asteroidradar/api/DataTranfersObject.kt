package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid


@JsonClass(generateAdapter = true)
data class  NetworkContainer (val asteroids : List<Asteroid>)

@JsonClass(generateAdapter = true)
data class AsteroidNetwork(val id: Long,
                           val codename: String,
                           val closeApproachDate: String,
                           val absoluteMagnitude: Double,
                           val estimatedDiameter: Double,
                           val relativeVelocity: Double,
                           val distanceFromEarth: Double,
                           val isPotentiallyHazardous: Boolean)