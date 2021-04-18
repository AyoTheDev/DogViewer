package com.ayo.domain

import com.ayo.domain.model.DogDomain
import kotlinx.coroutines.flow.Flow

interface ApiService {

    fun getDogs(): Flow<List<DogDomain>>

    fun searchDogsByName(query: String): Flow<List<DogDomain>>
}