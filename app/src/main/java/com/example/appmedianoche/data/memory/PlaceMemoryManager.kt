package com.example.appmedianoche.data.memory

import com.example.appmedianoche.domain.model.Place
import com.example.appmedianoche.domain.repository.IPlaceRepository

class PlaceMemoryManager : IPlaceRepository {
    private val places = mutableListOf<Place>()

    override fun getPlaces(): List<Place> = TODO()
    override fun getById(id: String): Place? = TODO()
    override fun addPlace(place: Place) = TODO()
    override fun updatePlace(place: Place) = TODO()
    override fun deletePlace(id: String) = TODO()
}