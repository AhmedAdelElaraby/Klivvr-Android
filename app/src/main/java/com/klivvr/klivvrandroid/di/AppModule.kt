package com.klivvr.klivvrandroid.di

import android.content.Context
import com.klivvr.klivvrandroid.data.repository.CityRepositoryImpl
import com.klivvr.klivvrandroid.domain.repository.CityRepository
import com.klivvr.klivvrandroid.domain.usecase.SearchCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCityRepository(@ApplicationContext context: Context): CityRepository {
        return CityRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideSearchCitiesUseCase(repo: CityRepository): SearchCitiesUseCase = SearchCitiesUseCase(repo)
}