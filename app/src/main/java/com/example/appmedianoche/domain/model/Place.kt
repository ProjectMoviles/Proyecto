package com.example.appmedianoche.domain.model

data class Place(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)