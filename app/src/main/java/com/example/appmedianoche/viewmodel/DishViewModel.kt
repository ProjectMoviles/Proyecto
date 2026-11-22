package com.example.appmedianoche.viewmodel

import android.R
import androidx.lifecycle.ViewModel
import com.example.appmedianoche.data.memory.DishMemoryManager
import com.example.appmedianoche.domain.model.Dish
import com.example.appmedianoche.domain.model.DishType
import com.example.appmedianoche.domain.repository.IDishRepository


class DishViewModel : ViewModel() {

    companion object{
        private var repository: IDishRepository = DishMemoryManager()
        private var nextId: Int =1

        fun getNextId(): String{
        val id =nextId
        nextId++
        return id.toString()
        }

        fun getDishes(): List<Dish> = repository.getDishes()

        fun getDishesById(id: String): Dish? = repository.getById(id)

        fun filterDishes(
            query: String = "",
            minRarity: Int = 1,
            maxPrice: Int? = null,
            type: DishType? = null,
            minRating: Double = 0.0
        ): List<Dish> {
            return repository.getDishes().filter { dish ->
                (dish.name.contains(query, ignoreCase = true) ||
                        dish.placeId.contains(query, ignoreCase = true)) &&
                        dish.rarityLevel >= minRarity &&
                        (maxPrice == null || dish.price <= maxPrice) &&
                        (type == null || dish.type == type) &&
                        dish.deliciousScore >= minRating
            }
        }

        fun createDish(dish: Dish){
            if (repository.getById(dish.id)== null)
                repository.addDish(dish)
            else
                throw Exception("The dish is duplicated. Please add a new one")
        }

        fun updateDish(dish: Dish){
            if (repository.getById(dish.id) != null)
                repository.updateDish(dish)
            else
                throw Exception("It's not possible to update because it does not exist")
        }

        fun deleteDish(id: String){
            if (repository.getById(id) != null)
                repository.deleteDish(id)
            else
                throw Exception("It's not possible to delete the dish because it does not exist.")
        }
    }
}