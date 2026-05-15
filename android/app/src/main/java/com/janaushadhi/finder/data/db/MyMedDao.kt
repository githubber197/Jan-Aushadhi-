package com.janaushadhi.finder.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.janaushadhi.finder.data.model.MyMed

@Dao
interface MyMedDao {

    @Query("SELECT * FROM my_meds ORDER BY refillDate ASC")
    fun getAll(): LiveData<List<MyMed>>

    @Query("SELECT * FROM my_meds ORDER BY refillDate ASC")
    suspend fun getAllSync(): List<MyMed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(med: MyMed): Long

    @Delete
    suspend fun delete(med: MyMed)

    @Query("DELETE FROM my_meds WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM my_meds")
    suspend fun count(): Int
}
