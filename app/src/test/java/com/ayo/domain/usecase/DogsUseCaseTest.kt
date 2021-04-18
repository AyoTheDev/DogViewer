package com.ayo.domain.usecase

import com.ayo.doggallery.common.DogBuilder
import com.ayo.domain.ApiService
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DogsUseCaseTest {

    @Mock
    lateinit var apiService: ApiService

    private lateinit var underTest: DogsUseCase

    private val apiResponseSuccessFull = listOf(DogBuilder.build())


    @Before
    fun setUp() {
        underTest = DogsUseCase(apiService)
    }

    @Test
    fun `verify dog list is fetch successfully`() = runBlockingTest {

        given(apiService.getDogs()).willReturn(flow { emit(apiResponseSuccessFull) })

        val result = underTest.getDogs().toList()

        verify(apiService).getDogs()
        assertTrue(result.isNotEmpty())
        assertEquals(apiResponseSuccessFull, result.first())
    }

}