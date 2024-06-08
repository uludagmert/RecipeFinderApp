package com.example.recipefinderapp.detail.repository

import com.example.recipefinderapp.detail.model.DetailResponse
import com.example.recipefinderapp.detail.service.IDetailService
import javax.inject.Inject

interface IDetailRepository{

    suspend fun getDetailsOfDish (id : String) : DetailResponse
}

class DetailRepository @Inject constructor(
    val service : IDetailService
): IDetailRepository{
    override suspend fun getDetailsOfDish(id: String): DetailResponse {
        return service.GetDetailsForDish(id)
    }

}