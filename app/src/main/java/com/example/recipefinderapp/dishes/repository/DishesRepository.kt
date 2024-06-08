package com.example.recipefinderapp.dishes.repository


import com.example.recipefinderapp.db.DishesDao
import com.example.recipefinderapp.dishes.model.DishesResponse
import com.example.recipefinderapp.dishes.service.IDishesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface IDishesRepository {
    suspend fun getDishesForCategory(categoryName: String) : DishesResponse
}
class DishesRepository @Inject constructor(
    val service: IDishesService,
    val dao: DishesDao,
    val dispatcher: CoroutineDispatcher

): IDishesRepository {
    override suspend fun getDishesForCategory(categoryName: String): DishesResponse {
       return withContext(dispatcher){
            try {
            val response =  service.getDishesForCategory(categoryName)
            response.meals.forEach{
                it.categoryId = categoryName
            }
            dao.saveDishesResponse(response.meals)
            response
        }catch (e:Exception){
            val list = dao.getDishesForCategory(categoryName)
            DishesResponse(list)
        }
       }
    }
}