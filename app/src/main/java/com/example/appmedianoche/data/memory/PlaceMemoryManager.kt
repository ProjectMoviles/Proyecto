package com.example.appmedianoche.data.memory

import com.example.appmedianoche.domain.model.Place
import com.example.appmedianoche.domain.repository.IPlaceRepository

class PlaceMemoryManager : IPlaceRepository {
    private val places = mutableListOf<Place>()

    override fun getPlaces(): List<Place> = places

    override fun getById(id: String): Place? =
        places.firstOrNull { it.id.trim() == id.trim() }

    override fun addPlace(place: Place) {
        places.add(place)
    }

    override fun updatePlace(place: Place) {
        deletePlace(place.id)
        addPlace(place)
    }

    override fun deletePlace(id: String) {
        places.removeIf { it.id.trim() == id.trim() }
    }
}