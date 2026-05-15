package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.janaushadhi.finder.JanAushadhiApp
import com.janaushadhi.finder.data.model.MyMed
import kotlinx.coroutines.launch

class MyMedicinesViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = (application as JanAushadhiApp).repository

    val myMeds: LiveData<List<MyMed>> = repo.getMyMeds()

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    fun addMed(name: String, refillDate: String, qty: Int) {
        viewModelScope.launch {
            if (name.isBlank() || refillDate.isBlank()) {
                _message.value = "Please fill all fields"
                return@launch
            }
            repo.addMyMed(MyMed(name = name, refillDate = refillDate, quantityPerMonth = qty))
            _message.value = "✅ Reminder set for $name"
        }
    }

    fun deleteMed(id: Int) {
        viewModelScope.launch { repo.deleteMyMed(id) }
    }

    fun clearMessage() { _message.value = null }
}
