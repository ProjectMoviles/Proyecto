package com.example.appmedianoche.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmedianoche.domain.model.Place
import com.example.appmedianoche.domain.repository.IPlaceRepository

class PlaceViewModel(private val repository: IPlaceRepository) : ViewModel() {

    fun getPlaces(): List<Place> = repository.getPlaces()

    fun getPlaceById(id: String): Place? = repository.getById(id)

    fun createPlace(place: Place) {
        if (repository.getById(place.id) == null)
            repository.addPlace(place)
        else
            throw Exception("The place is duplicated. Please add a new one.")
    }

    fun updatePlace(place: Place) {
        if (repository.getById(place.id) != null)
            repository.updatePlace(place)
        else
            throw Exception("It's not possible to update the place because it does not exist.")
    }

    fun deletePlace(id: String) {
        if (repository.getById(id) != null)
            repository.deletePlace(id)
        else
            throw Exception("It's not possible to delete the place because it does not exist.")
    }
}
