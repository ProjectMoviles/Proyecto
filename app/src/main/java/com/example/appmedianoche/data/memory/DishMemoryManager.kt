package com.example.appmedianoche.data.memory

import com.example.appmedianoche.domain.model.Dish
import com.example.appmedianoche.domain.repository.IDishRepository

class DishMemoryManager : IDishRepository {
    private val dishes = mutableListOf<Dish>()

    override fun getDishes(): List<Dish> = dishes

    override fun getById(id: String): Dish? =
        dishes.firstOrNull { it.id.trim() == id.trim() }

    override fun addDish(dish: Dish) {
        dishes.add(dish)
    }

    override fun updateDish(dish: Dish) {
        deleteDish(dish.id)
        addDish(dish)
    }

    override fun deleteDish(id: String) {
        dishes.removeIf { it.id.trim() == id.trim() }
    }
}