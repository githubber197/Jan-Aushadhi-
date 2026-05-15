package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.janaushadhi.finder.JanAushadhiApp
import com.janaushadhi.finder.data.model.Medicine
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = (application as JanAushadhiApp).repository

    private val _medicine = MutableLiveData<Medicine?>()
    val medicine: LiveData<Medicine?> = _medicine

    private val _genAiResult = MutableLiveData<String?>()
    val genAiResult: LiveData<String?> = _genAiResult

    private val _isAiLoading = MutableLiveData(false)
    val isAiLoading: LiveData<Boolean> = _isAiLoading

    fun loadMedicine(id: Int) {
        viewModelScope.launch { _medicine.value = repo.getMedicineById(id) }
    }

    fun setMedicine(medicine: Medicine) { _medicine.value = medicine }

    fun askGenAi(brandName: String) {
        viewModelScope.launch {
            _isAiLoading.value = true
            _genAiResult.value = null
            try {
                val model = com.google.ai.client.generativeai.GenerativeModel(
                    modelName = com.janaushadhi.finder.utils.Constants.GEMINI_MODEL,
                    apiKey   = com.janaushadhi.finder.utils.Constants.GEMINI_API_KEY
                )
                val prompt = """
                    You are a pharmacist assistant in India. 
                    For the branded medicine "$brandName", identify:
                    1. The generic salt name
                    2. Typical Jan-Aushadhi Kendra price (₹)
                    3. One-line plain-language explanation of what it treats
                    Reply in this exact format:
                    SALT: <salt name>
                    PRICE: ₹<price>
                    USES: <uses>
                """.trimIndent()
                val response = model.generateContent(prompt)
                _genAiResult.value = response.text ?: "Could not get AI response."
            } catch (e: Exception) {
                _genAiResult.value = "GenAI unavailable: ${e.message}\nPlease verify with your pharmacist."
            } finally {
                _isAiLoading.value = false
            }
        }
    }
}
