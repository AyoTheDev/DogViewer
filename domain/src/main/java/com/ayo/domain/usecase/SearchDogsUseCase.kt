package com.ayo.domain.usecase

import com.ayo.domain.ApiService
import javax.inject.Inject

//if we we were persisting data we would have a repository that would have the api service
//and our persistence library
class SearchDogsUseCase @Inject constructor(private val apiService: ApiService) {

    fun searchDogs(query: String) = apiService.searchDogsByName(query)

}
