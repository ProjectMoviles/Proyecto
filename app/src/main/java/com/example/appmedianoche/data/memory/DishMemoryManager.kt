package com.example.appmedianoche.data.memory

import com.example.appmedianoche.domain.model.Dish
import com.example.appmedianoche.domain.repository.IDishRepository

class DishMemoryManager : IDishRepository {
    private val dishes = mutableListOf<Dish>()

    override fun getDishes(): List<Dish> = TODO()
    override fun getById(id: String): Dish? = TODO()
    override fun addDish(dish: Dish) = TODO()
    override fun updateDish(dish: Dish) = TODO()
    override fun deleteDish(id: String) = TODO()
}