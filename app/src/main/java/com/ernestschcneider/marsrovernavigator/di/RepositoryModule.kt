package com.ernestschcneider.marsrovernavigator.di

import com.ernestschcneider.marsrovernavigator.data.repo.RoverRepositoryImpl
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRoverRepository(
        impl: RoverRepositoryImpl
    ): RoverRepository
}
