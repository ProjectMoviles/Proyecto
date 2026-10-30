package com.example.appmedianoche.domain.model

data class Dish(
    val id: String,
    val name: String,
    val placeId: String,
    val price: Int,
    val type: DishType,
    val rarityLevel: Int,        // 1 to 5
    val deliciousScore: Double,  // 1.0 (gross) to 5.0 (delicious)
    val description: String,
    val photoUri: String?
)