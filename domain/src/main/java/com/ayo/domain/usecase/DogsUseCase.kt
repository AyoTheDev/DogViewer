package com.ayo.domain.usecase

import com.ayo.domain.ApiService
import com.ayo.domain.model.DogDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//if we we were persisting data we would have a repository that would have the api service
//and our persistence library
class DogsUseCase @Inject constructor(private val apiService: ApiService) {

    fun getDogs(): Flow<List<DogDomain>> = apiService.getDogs()
}
