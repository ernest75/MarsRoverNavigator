package com.ernestschcneider.marsrovernavigator.di

import com.ernestschcneider.marsrovernavigator.data.network.RoverFakeApiService
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRoverApiService(): RoverApiService {
        return RoverFakeApiService()
    }
}