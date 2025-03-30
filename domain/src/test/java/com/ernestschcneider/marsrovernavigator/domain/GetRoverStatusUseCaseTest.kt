package com.ernestschcneider.marsrovernavigator.domain

import com.ernestschcneider.marsrovernavigator.core.sharedutils.testhelper.TestJsonFactory
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
class GetRoverStatusUseCaseTest {

    private val repo = mockk<RoverRepository>(relaxed = true)
    private val useCase = GetRoverStatusUseCase(repo)

    @Test
    fun `WHEN invoke THEN call to repo happens with object send`() = runTest {
        // Given
        val json = TestJsonFactory.createRoverJson()

        // When
        useCase.invoke(json)

        // Then
        coVerify(exactly = 1) { repo.getRoverStatus(json) }
    }
}
