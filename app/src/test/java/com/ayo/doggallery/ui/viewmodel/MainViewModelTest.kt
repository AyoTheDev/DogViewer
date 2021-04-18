package com.ayo.doggallery.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ayo.doggallery.common.DogBuilder
import com.ayo.doggallery.common.TestContextProvider
import com.ayo.doggallery.common.getOrAwaitValue
import com.ayo.doggallery.utils.ApiError
import com.ayo.doggallery.utils.Resource
import com.ayo.domain.model.DogDomain
import com.ayo.domain.usecase.DogsUseCase
import com.ayo.domain.usecase.SearchDogsUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var underTest: MainViewModel

    @Mock
    lateinit var getDogsUseCase: DogsUseCase

    @Mock
    lateinit var searchDogsUseCase: SearchDogsUseCase

    private val apiResponseSuccessFull = listOf(DogBuilder.build())

    private val emptyServerResponse = emptyList<DogDomain>()

    @Before
    fun setUp() {
        underTest =
            MainViewModel(
                TestContextProvider(),
                searchDogsUseCase,
                getDogsUseCase
            )

    }


    @Test
    fun `assert empty dog list from server response when get dog list is invoked`(): Unit =
        runBlockingTest {

            given(getDogsUseCase.getDogs()).willReturn(flow { emit(emptyServerResponse) })

            underTest.getDogsList()

            verify(getDogsUseCase, atLeastOnce()).getDogs()

            assertEquals(
                underTest.dogsLiveData.getOrAwaitValue(),
                Resource.Success<List<DogDomain>>(emptyList())
            )

        }

    @Test
    fun `asserts a list of dogs is returned from the server`(): Unit = runBlockingTest {

        given(getDogsUseCase.getDogs()).willReturn(flow { emit(apiResponseSuccessFull) })

        underTest.getDogsList()

        assertEquals(
            underTest.dogsLiveData.getOrAwaitValue(),
            Resource.Success(apiResponseSuccessFull)
        )

        assertEquals((underTest.dogsLiveData.getOrAwaitValue() as Resource.Success).data.size, 1)

    }


    @Test
    fun `assert empty dog list from server response when search dog list is invoked`(): Unit =
        runBlockingTest {

            given(searchDogsUseCase.searchDogs(any())).willReturn(flow { emit(emptyServerResponse) })

            underTest.searchDogs("zzss")

            verify(getDogsUseCase, atLeastOnce()).getDogs()

            assertEquals(
                underTest.dogsLiveData.getOrAwaitValue(),
                Resource.Success<List<DogDomain>>(emptyList())
            )

        }


    @Test
    fun `asserts a list dogs is returned from the server when search dogs is invoked`(): Unit =
        runBlockingTest {

            val query = "Affenpinscher"
            given(searchDogsUseCase.searchDogs(query)).willReturn(flow {
                emit(apiResponseSuccessFull)
            })

            underTest.searchDogs(query)

            assertEquals(
                underTest.dogsLiveData.getOrAwaitValue(),
                Resource.Success(apiResponseSuccessFull)
            )

            assertEquals(
                (underTest.dogsLiveData.getOrAwaitValue() as Resource.Success).data.size,
                1
            )

        }

}