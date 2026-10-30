package com.example.appmedianoche.domain.repository

import com.example.appmedianoche.domain.model.Dish

interface IDishRepository {
    fun getDishes(): List<Dish>
    fun getById(id: String): Dish?
    fun addDish(dish: Dish)
    fun updateDish(dish: Dish)
    fun deleteDish(id: String)
}