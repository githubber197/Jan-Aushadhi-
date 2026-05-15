package com.janaushadhi.finder.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.janaushadhi.finder.data.model.Medicine

@Dao
interface MedicineDao {

    @Query("SELECT * FROM medicines ORDER BY brand ASC")
    fun getAll(): LiveData<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE category = :category ORDER BY brand ASC")
    fun getByCategory(category: String): LiveData<List<Medicine>>

    @Query("""
        SELECT * FROM medicines
        WHERE brand LIKE :query OR generic LIKE :query OR salt LIKE :query
        ORDER BY brand ASC LIMIT 20
    """)
    suspend fun search(query: String): List<Medicine>

    @Query("SELECT * FROM medicines WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Medicine?

    // Room KSP does not support default parameter values — pass explicit limit from call site
    @Query("SELECT * FROM medicines ORDER BY brand ASC LIMIT :limit")
    suspend fun getPopular(limit: Int): List<Medicine>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicines: List<Medicine>)

    @Query("DELETE FROM medicines")
    suspend fun clearAll()

    // Atomic refresh: delete old rows and insert new ones in a single transaction
    // so the DB is never left empty if the app dies mid-operation
    @Transaction
    suspend fun replaceAll(medicines: List<Medicine>) {
        clearAll()
        insertAll(medicines)
    }

    @Query("SELECT COUNT(*) FROM medicines")
    suspend fun count(): Int
}
