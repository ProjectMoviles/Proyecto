package com.example.appmedianoche.domain.repository

import com.example.appmedianoche.domain.model.Place

interface IPlaceRepository {
    fun getPlaces(): List<Place>
    fun getById(id: String): Place?
    fun addPlace(place: Place)
    fun updatePlace(place: Place)
    fun deletePlace(id: String)
}