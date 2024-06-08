package com.example.recipefinderapp.di

import com.example.recipefinderapp.category.repository.CategoryRepository
import com.example.recipefinderapp.category.repository.ICategoryRepository
import com.example.recipefinderapp.category.service.ICategoryService
import com.example.recipefinderapp.category.usecase.GetCategoriesUseCase
import com.example.recipefinderapp.category.usecase.IGetCategoriesUseCase
import com.example.recipefinderapp.detail.repository.DetailRepository
import com.example.recipefinderapp.detail.repository.IDetailRepository
import com.example.recipefinderapp.detail.service.IDetailService
import com.example.recipefinderapp.detail.usecase.GetDetailsUseCase
import com.example.recipefinderapp.detail.usecase.IGetDetailsUseCase
import com.example.recipefinderapp.dishes.repository.DishesRepository
import com.example.recipefinderapp.dishes.repository.IDishesRepository
import com.example.recipefinderapp.dishes.service.IDishesService
import com.example.recipefinderapp.dishes.usecase.GetDishesUseCase
import com.example.recipefinderapp.dishes.usecase.IGetDishesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providesCategoryService(retrofit: Retrofit): ICategoryService{
    return retrofit.create(ICategoryService::class.java)
    }
    @Provides
    @Singleton
    fun providesDishesService(retrofit: Retrofit): IDishesService{
        return retrofit.create(IDishesService::class.java)
    }

    @Provides
    @Singleton
    fun providesDetailsService(retrofit: Retrofit): IDetailService{
        return retrofit.create(IDetailService::class.java)
    }
    @Provides
    @Singleton
    fun provideDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {
        @Binds
        @Singleton
        fun provideCategoryRepository (
            repository: CategoryRepository
        ) : ICategoryRepository

        @Binds
        @Singleton
        fun provideDishesRepository (
            repository: DishesRepository
        ) : IDishesRepository

        @Binds
        @Singleton
        fun provideDetailRepository (
            repository: DetailRepository
        ) : IDetailRepository

        @Binds
        @Singleton
        fun provideGetCategoryUseCase(uc : GetCategoriesUseCase) : IGetCategoriesUseCase

        @Binds
        @Singleton
        fun provideGetDishesUseCase(uc : GetDishesUseCase) : IGetDishesUseCase

        @Binds
        @Singleton
        fun provideGetDetailUseCase(uc : GetDetailsUseCase) : IGetDetailsUseCase
    }
}