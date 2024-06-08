package com.example.recipefinderapp.category.repository

import android.util.Log
import com.example.recipefinderapp.category.model.CategoryResponse
import com.example.recipefinderapp.category.service.ICategoryService
import com.example.recipefinderapp.db.DishesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ICategoryRepository {
    suspend fun getAllCategories () : CategoryResponse
}

class CategoryRepository @Inject constructor(
    val service : ICategoryService,
    val dao : DishesDao,
    val dispatcher: CoroutineDispatcher

): ICategoryRepository {
    override suspend fun getAllCategories(): CategoryResponse {
        return withContext(dispatcher){
            val response =  try {
                val hold = service.getAllCategories()
                dao.saveCategoryResponse(hold.categories)
                hold
            }catch (e:Exception){
                CategoryResponse(dao.getAllCategories())

            }
            response

        }

    }

}