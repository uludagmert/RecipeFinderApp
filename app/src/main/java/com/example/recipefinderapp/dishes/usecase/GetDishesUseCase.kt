package com.example.recipefinderapp.dishes.usecase

import com.example.recipefinderapp.dishes.model.DishesResponse
import com.example.recipefinderapp.dishes.repository.IDishesRepository
import javax.inject.Inject

interface IGetDishesUseCase {
    suspend operator fun invoke(categoryName : String) : DishesResponse

}

class GetDishesUseCase @Inject constructor(
    val repo : IDishesRepository
) : IGetDishesUseCase{
    override suspend fun invoke(categoryName: String): DishesResponse{

        return repo.getDishesForCategory(categoryName)
    }
}