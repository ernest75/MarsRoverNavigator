package com.ernestschcneider.marsrovernavigator.di

import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import com.ernestschcneider.marsrovernavigator.domain.usecase.InitialContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRoverStatusUseCase(
        roverRepository: RoverRepository
    ): GetRoverStatusUseCase = GetRoverStatusUseCase(roverRepository)

    @Provides
    @Singleton
    fun provideInitialContactUseCase(
        roverRepository: RoverRepository
    ): InitialContactUseCase = InitialContactUseCase(roverRepository)
}
