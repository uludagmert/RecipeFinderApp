package com.example.recipefinderapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipefinderapp.db.DishesDao
import com.example.recipefinderapp.db.RecipeFinderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun ProvideDB (@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        RecipeFinderDatabase::class.java,
        "recipe_database"

    )
        .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDBDao(db: RecipeFinderDatabase) : DishesDao{
        return db.provideDao()
    }
}