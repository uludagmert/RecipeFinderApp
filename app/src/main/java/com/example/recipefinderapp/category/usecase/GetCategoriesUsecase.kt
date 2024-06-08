package com.example.recipefinderapp.category.usecase

import com.example.recipefinderapp.category.model.CategoryResponse
import com.example.recipefinderapp.category.repository.ICategoryRepository
import javax.inject.Inject

interface IGetCategoriesUseCase {

    suspend operator fun invoke () : CategoryResponse

}
class GetCategoriesUseCase @Inject constructor(
    val repository : ICategoryRepository

) : IGetCategoriesUseCase {

    override suspend fun invoke(): CategoryResponse {
         return repository.getAllCategories()
    }
}