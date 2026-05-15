package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.janaushadhi.finder.JanAushadhiApp
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.search.FuzzySearchHelper
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = (application as JanAushadhiApp).repository

    private val _searchResults = MutableLiveData<List<Medicine>>(emptyList())
    val searchResults: LiveData<List<Medicine>> = _searchResults

    private val _popular = MutableLiveData<List<Medicine>>(emptyList())
    val popular: LiveData<List<Medicine>> = _popular

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _backendOnline = MutableLiveData(true)
    val backendOnline: LiveData<Boolean> = _backendOnline

    // All cached medicines for fuzzy search
    private var allMedicines: List<Medicine> = emptyList()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            // Refresh from network into Room cache
            val result = repo.refreshMedicines()
            _backendOnline.value = result.isSuccess
            // Load popular list
            allMedicines = repo.getPopular(100)
            _popular.value = allMedicines.take(12)
            _isLoading.value = false
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.length < 2) {
                _searchResults.value = emptyList()
                return@launch
            }
            // Fuzzy search on cached list
            val fuzzy = FuzzySearchHelper.search(query, allMedicines)
            // If fuzzy empty, try Room query
            val results = if (fuzzy.isNotEmpty()) fuzzy else repo.searchMedicines(query)
            _searchResults.value = results
        }
    }

    fun filterByCategory(category: String) {
        viewModelScope.launch {
            _popular.value = if (category == "all") allMedicines.take(12)
            else allMedicines.filter { it.category == category }
        }
    }

    fun clearSearch() { _searchResults.value = emptyList() }
}
