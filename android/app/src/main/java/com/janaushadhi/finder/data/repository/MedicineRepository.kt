package com.janaushadhi.finder.data.repository

import androidx.lifecycle.LiveData
import com.janaushadhi.finder.data.db.AppDatabase
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.model.MyMed
import com.janaushadhi.finder.data.model.Store
import com.janaushadhi.finder.data.network.RetrofitClient
import com.janaushadhi.finder.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MedicineRepository(private val db: AppDatabase) {

    private val medicineDao = db.medicineDao()
    private val myMedDao    = db.myMedDao()
    private val api         = RetrofitClient.apiService

    fun getAllMedicines(): LiveData<List<Medicine>> = medicineDao.getAll()

    fun getMedicinesByCategory(category: String): LiveData<List<Medicine>> =
        medicineDao.getByCategory(category)

    suspend fun getPopular(limit: Int = 12): List<Medicine> =
        withContext(Dispatchers.IO) { medicineDao.getPopular(limit) }

    suspend fun searchMedicines(query: String): List<Medicine> =
        withContext(Dispatchers.IO) { medicineDao.search("%$query%") }

    suspend fun getMedicineById(id: Int): Medicine? =
        withContext(Dispatchers.IO) { medicineDao.getById(id) }

    suspend fun refreshMedicines(): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val response = api.getMedicines(limit = 100)
            if (response.isSuccessful) {
                val medicines = response.body()?.data ?: emptyList()
                if (medicines.isNotEmpty()) {
                    medicineDao.replaceAll(medicines)
                }
                Result.success(medicines.size)
            } else {
                Timber.e("Medicine API Error: ${response.code()}")
                Result.failure(Exception("API ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Network unavailable")
            Result.failure(e)
        }
    }

    suspend fun getStores(lat: Double? = null, lng: Double? = null, radius: Int = 10): Result<List<Store>> = withContext(Dispatchers.IO) {
        try {
            var resp: retrofit2.Response<com.janaushadhi.finder.data.network.StoresResponse>? = null
            
            if (lat != null && lng != null) {
                try {
                    resp = api.getNearbyStores(lat, lng, radius)
                    if (!resp.isSuccessful) {
                        Timber.w("Nearby stores endpoint returned ${resp.code()}, falling back")
                        resp = null
                    }
                } catch (e: Exception) {
                    Timber.w(e, "Nearby stores endpoint threw exception, falling back")
                    resp = null
                }
            }
            
            if (resp == null) {
                resp = api.getStores()
            }

            if (resp.isSuccessful) {
                Result.success(resp?.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception("Stores API ${resp.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Could not load stores")
            Result.failure(e)
        }
    }

    fun getMyMeds(): LiveData<List<MyMed>> = myMedDao.getAll()

    suspend fun getMyMedsSync(): List<MyMed> =
        withContext(Dispatchers.IO) { myMedDao.getAllSync() }

    suspend fun addMyMed(med: MyMed): Long =
        withContext(Dispatchers.IO) { myMedDao.insert(med) }

    suspend fun deleteMyMed(id: Int) =
        withContext(Dispatchers.IO) { myMedDao.deleteById(id) }
}
