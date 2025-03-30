package com.ernestschcneider.marsrovernavigator.domain

import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import com.ernestschcneider.marsrovernavigator.domain.usecase.InitialContactUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class InitialContactUseCaseTest {

    private val repo = mockk<RoverRepository>(relaxed = true)

    private val useCase = InitialContactUseCase(repo)

    @Test
    fun `WHEN invoke THEN call to repo`() = runTest {
        // When
        useCase.invoke()

        // Then
        coVerify(exactly = 1) { repo.getInitialContact() }
    }
}
