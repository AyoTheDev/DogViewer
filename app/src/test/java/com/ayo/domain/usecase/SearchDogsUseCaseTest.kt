package com.ayo.domain.usecase

import com.ayo.doggallery.common.DogBuilder
import com.ayo.domain.ApiService
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchDogsUseCaseTest {

    @Mock
    lateinit var apiService: ApiService

    private lateinit var underTest: SearchDogsUseCase

    private val apiResponseSuccessFull = listOf(DogBuilder.build())


    @Before
    fun setUp() {
        underTest = SearchDogsUseCase(apiService)
    }

    @Test
    fun `verify search for dogs is fetch successfully`() = runBlockingTest {
        val query = "dog"

        given(apiService.searchDogsByName(query)).willReturn(flow { emit(apiResponseSuccessFull) })

        val result = underTest.searchDogs(query).toList()

        assertTrue(result.isNotEmpty())
        verify(apiService).searchDogsByName(query)
        assertEquals(apiResponseSuccessFull,result.first())

    }
}