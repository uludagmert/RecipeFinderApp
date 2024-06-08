package com.example.recipefinderapp.dishes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

data class DishesResponse(
    val meals: List<Meal>
)

/*class Converter {
    @TypeConverter
    fun toString (listOfMeal : List<Meal>) : String{
        return listOfMeal.map {
            "${it.idMeal}---${it.strMeal}---${it.strMealThumb}"
        }.joinToString (separator = ",")

    }

    @TypeConverter
    fun toObject (string: String) : List<Meal>{
        var output = mutableListOf<Meal>()
        string.split(",").forEach {
            val split =it.split("---")
            output.add(Meal(split[0],split[1],split[2]))
        }
        return output
    }
}*/