package com.ayo.api.services

import com.ayo.api.endpoints.EndPoints
import com.ayo.api.toDomain
import com.ayo.domain.ApiService
import com.ayo.domain.model.DogDomain
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val endpoints: EndPoints) : ApiService {

    override fun getDogs() = flow<List<DogDomain>> {
        val response = endpoints.getDogs().map {
            it.toDomain()
        }

        emit(response)
    }


    override fun searchDogsByName(query: String) = flow<List<DogDomain>> {
        val response = endpoints.searchDogs(query).map {
            it.toDomain()
        }

        emit(response)
    }
}
