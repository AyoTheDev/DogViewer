package com.ayo.doggallery.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ayo.domain.model.DogDomain
import com.ayo.domain.usecase.DogsUseCase
import com.ayo.domain.usecase.SearchDogsUseCase
import com.ayo.doggallery.common.BaseViewModel
import com.ayo.doggallery.common.CoroutineContextProvider
import com.ayo.doggallery.ui.extensions.asLiveData
import com.ayo.doggallery.utils.ApiError
import com.ayo.doggallery.utils.Resource
import com.ayo.doggallery.utils.Resource.Success
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val searchDogsUseCase: SearchDogsUseCase,
    private val dogsUseCase: DogsUseCase
) : BaseViewModel(coroutineContextProvider) {

    private val _dogsLiveData = MutableLiveData<Resource<List<DogDomain>>>()
    val dogsLiveData = _dogsLiveData.asLiveData()


    init {
        getDogsList()
    }


    fun searchDogs(query: String) {
        load(
            launch {
                searchDogsUseCase
                    .searchDogs(query)
                    .onStart { _dogsLiveData.postValue(Resource.Loading(true)) }
                    .catch {
                        val message = ApiError.extractErrorMessage(it)
                        _dogsLiveData.postValue(Resource.Failure(message))
                        Timber.e(it)
                    }.collect { dogs ->
                        _dogsLiveData.postValue(Success(dogs))
                    }
            }
        )
    }

    fun getDogsList() {
        launch {
            dogsUseCase
                .getDogs()
                .onStart {
                    _dogsLiveData.postValue(Resource.Loading(true))

                }
                .catch {
                    val message = ApiError.extractErrorMessage(it)
                    _dogsLiveData.postValue(Resource.Failure(message))
                    Timber.e(it)
                }.collect { dogs ->
                    _dogsLiveData.postValue(Success(dogs))
                }
        }
    }
}
