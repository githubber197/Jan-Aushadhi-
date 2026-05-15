package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.janaushadhi.finder.JanAushadhiApp
import com.janaushadhi.finder.data.model.Store
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = (application as JanAushadhiApp).repository

    private val _stores = MutableLiveData<List<Store>>(emptyList())
    val stores: LiveData<List<Store>> = _stores

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadStores(lat: Double? = null, lng: Double? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repo.getStores(lat, lng)
            
            if (result.isSuccess) {
                _stores.value = result.getOrNull() ?: emptyList()
            } else {
                val exception = result.exceptionOrNull()
                _error.value = when (exception) {
                    is SocketTimeoutException -> "Backend is waking up (Render cold start). Please wait and retry..."
                    else -> "Connection failed. Check if the backend URL is correct."
                }
            }
            _isLoading.value = false
        }
    }
}
