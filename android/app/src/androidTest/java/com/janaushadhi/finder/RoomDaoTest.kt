package com.janaushadhi.finder

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.janaushadhi.finder.data.db.AppDatabase
import com.janaushadhi.finder.data.db.MedicineDao
import com.janaushadhi.finder.data.db.MyMedDao
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.model.MyMed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests for Room DAOs using an in-memory database.
 * Runs on a real Android device/emulator (no mock framework needed).
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RoomDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var medicineDao: MedicineDao
    private lateinit var myMedDao: MyMedDao

    @Before
    fun setup() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(ctx, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        medicineDao = db.medicineDao()
        myMedDao    = db.myMedDao()
    }

    @After
    fun teardown() { db.close() }

    // ── MedicineDao ───────────────────────────────────────────────────────────

    @Test
    fun insertAllAndCount() = runTest {
        val meds = sampleMedicines(5)
        medicineDao.insertAll(meds)
        assertEquals(5, medicineDao.count())
    }

    @Test
    fun clearAllRemovesAllRows() = runTest {
        medicineDao.insertAll(sampleMedicines(10))
        medicineDao.clearAll()
        assertEquals(0, medicineDao.count())
    }

    @Test
    fun replaceAllIsAtomic() = runTest {
        medicineDao.insertAll(sampleMedicines(5))
        val newMeds = sampleMedicines(3).map { it.copy(id = it.id + 100) }
        medicineDao.replaceAll(newMeds)
        assertEquals(3, medicineDao.count())
    }

    @Test
    fun searchByBrandReturnsCorrectResult() = runTest {
        medicineDao.insertAll(listOf(
            medicine(id = 1, brand = "Paracetamol", generic = "Acetaminophen"),
            medicine(id = 2, brand = "Metformin",   generic = "Metformin"),
        ))
        val results = medicineDao.search("%Para%")
        assertEquals(1, results.size)
        assertEquals(1, results[0].id)
    }

    @Test
    fun searchByGenericReturnsCorrectResult() = runTest {
        medicineDao.insertAll(listOf(
            medicine(id = 1, brand = "Crocin",   generic = "Paracetamol"),
            medicine(id = 2, brand = "Metformin", generic = "Metformin"),
        ))
        val results = medicineDao.search("%Paracetamol%")
        assertEquals(1, results.size)
        assertEquals(1, results[0].id)
    }

    @Test
    fun getPopularReturnsLimitedResults() = runTest {
        medicineDao.insertAll(sampleMedicines(20))
        val popular = medicineDao.getPopular(5)
        assertEquals(5, popular.size)
    }

    @Test
    fun getByIdReturnsCorrectMedicine() = runTest {
        medicineDao.insertAll(listOf(medicine(id = 42, brand = "TargetMed", generic = "GenericMed")))
        val result = medicineDao.getById(42)
        assertNotNull(result)
        assertEquals("TargetMed", result!!.brand)
    }

    @Test
    fun getByIdReturnsNullForMissingId() = runTest {
        val result = medicineDao.getById(999)
        assertNull(result)
    }

    // ── MyMedDao ──────────────────────────────────────────────────────────────

    @Test
    fun insertMyMedAndRetrieve() = runTest {
        val med = MyMed(name = "Metformin", refillDate = "2026-06-01")
        myMedDao.insert(med)
        val all = myMedDao.getAll()
        assertEquals(1, all.size)
        assertEquals("Metformin", all[0].name)
    }

    @Test
    fun deleteMyMedRemovesEntry() = runTest {
        val med = MyMed(name = "Amlopin", refillDate = "2026-06-01")
        myMedDao.insert(med)
        val inserted = myMedDao.getAll()[0]
        myMedDao.delete(inserted)
        assertEquals(0, myMedDao.getAll().size)
    }

    @Test
    fun getUrgentReturnsOnlyUrgentMeds() = runTest {
        myMedDao.insert(MyMed(name = "Med1", refillDate = "2020-01-01")) // overdue → urgent
        myMedDao.insert(MyMed(name = "Med2", refillDate = "2099-12-31")) // far future → not urgent
        val urgent = myMedDao.getUrgent()
        assertTrue(urgent.all { it.name == "Med1" })
    }

    // ── Stress: insert 500 medicines in one transaction ───────────────────────

    @Test
    fun stressInsert500MedicinesUnder2Seconds() = runTest {
        val meds = sampleMedicines(500)
        val start = System.currentTimeMillis()
        medicineDao.insertAll(meds)
        val elapsed = System.currentTimeMillis() - start
        assertEquals(500, medicineDao.count())
        assertTrue("Insert 500 took ${elapsed}ms, expected < 2000ms", elapsed < 2000)
    }

    @Test
    fun stressReplaceAll500Times() = runTest {
        repeat(10) { i ->
            val meds = sampleMedicines(50).map { it.copy(id = it.id + i * 50) }
            medicineDao.replaceAll(meds)
            assertEquals(50, medicineDao.count())
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun medicine(id: Int, brand: String, generic: String) = Medicine(
        id = id, brand = brand, generic = generic, salt = "Salt",
        manufacturer = "Mfr", brand_price = 100.0, generic_price = 20.0, category = "pain"
    )

    private fun sampleMedicines(count: Int) = (1..count).map { i ->
        Medicine(
            id = i, brand = "Brand$i", generic = "Generic$i", salt = "Salt$i",
            manufacturer = "Mfr$i", brand_price = (i * 10).toDouble(),
            generic_price = (i * 2).toDouble(), category = "pain"
        )
    }
}
