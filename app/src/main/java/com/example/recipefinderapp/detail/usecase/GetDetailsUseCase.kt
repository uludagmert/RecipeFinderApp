package com.example.recipefinderapp.detail.usecase

import com.example.recipefinderapp.detail.model.DetailResponse
import com.example.recipefinderapp.detail.repository.IDetailRepository
import javax.inject.Inject

interface  IGetDetailsUseCase {

   suspend operator fun invoke(id : String) : DetailResponse
}
class GetDetailsUseCase @Inject constructor(
    val repository : IDetailRepository
): IGetDetailsUseCase{

    override suspend fun invoke(id: String): DetailResponse {
        return repository.getDetailsOfDish(id)
    }
}