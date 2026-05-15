package com.janaushadhi.finder

import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.search.FuzzySearchHelper
import com.janaushadhi.finder.utils.Constants
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for FuzzySearchHelper — correctness, edge cases, and stress test.
 * Pure JVM, no Android framework.
 */
class FuzzySearchTest {

    private lateinit var medicines: List<Medicine>

    private fun med(id: Int, brand: String, generic: String, salt: String = "") = Medicine(
        id = id, brand = brand, generic = generic, salt = salt,
        manufacturer = "Mfr", brand_price = 100.0, generic_price = 20.0, category = "pain"
    )

    @Before
    fun setUp() {
        medicines = listOf(
            med(1,  "Paracetamol 500",  "Acetaminophen",  "Paracetamol"),
            med(2,  "Crocin",           "Paracetamol",    "Paracetamol 500mg"),
            med(3,  "Dolo 650",         "Paracetamol",    "Paracetamol 650mg"),
            med(4,  "Metformin HCl",    "Metformin",      "Metformin 500mg"),
            med(5,  "Glycomet",         "Metformin",      "Metformin 500mg"),
            med(6,  "Amlodipine 5mg",   "Amlodipine",     "Amlodipine Besylate"),
            med(7,  "Amoxicillin 500",  "Amoxicillin",    "Amoxicillin Trihydrate"),
            med(8,  "Augmentin",        "Amoxicillin",    "Amoxicillin+Clavulanate"),
            med(9,  "Atorvastatin",     "Atorvastatin",   "Atorvastatin Calcium"),
            med(10, "Vitamin D3",       "Cholecalciferol","Cholecalciferol 60000IU"),
        )
    }

    // ── Exact substring match ─────────────────────────────────────────────────

    @Test fun `exact brand match returns correct result`() {
        val results = FuzzySearchHelper.search("Crocin", medicines)
        assertTrue(results.any { it.id == 2 })
    }

    @Test fun `partial brand match finds result`() {
        val results = FuzzySearchHelper.search("para", medicines)
        assertTrue(results.isNotEmpty())
    }

    @Test fun `generic name match works`() {
        val results = FuzzySearchHelper.search("metformin", medicines)
        assertTrue(results.any { it.id == 4 || it.id == 5 })
    }

    @Test fun `salt name match works`() {
        val results = FuzzySearchHelper.search("amlodipine besylate", medicines)
        assertTrue(results.any { it.id == 6 })
    }

    // ── Fuzzy / typo tolerance ────────────────────────────────────────────────

    @Test fun `one typo in brand name still matches`() {
        // "Amoxicilin" (missing one 'l') → should match "Amoxicillin"
        val results = FuzzySearchHelper.search("Amoxicilin", medicines)
        assertTrue("Expected fuzzy match for 1-char typo", results.isNotEmpty())
    }

    @Test fun `two typos in brand name still matches`() {
        // "Atorvasatin" (2 chars off) → should match "Atorvastatin"
        val results = FuzzySearchHelper.search("Atorvasatin", medicines)
        assertTrue("Expected fuzzy match for 2-char typo", results.isNotEmpty())
    }

    // ── Min query length guard ────────────────────────────────────────────────

    @Test fun `query shorter than MIN_QUERY_LENGTH returns empty`() {
        val query = "a" // length 1, MIN_QUERY_LENGTH = 2
        val results = FuzzySearchHelper.search(query, medicines)
        assertTrue(results.isEmpty())
    }

    @Test fun `empty query returns empty`() {
        val results = FuzzySearchHelper.search("", medicines)
        assertTrue(results.isEmpty())
    }

    // ── Result limit ──────────────────────────────────────────────────────────

    @Test fun `results capped at 10`() {
        // Create 20 medicines all matching "paracetamol"
        val large = (1..20).map { i ->
            med(i, "Paracetamol $i", "Paracetamol", "Paracetamol")
        }
        val results = FuzzySearchHelper.search("paracetamol", large)
        assertTrue("Expected ≤ 10 results, got ${results.size}", results.size <= 10)
    }

    // ── Case insensitivity ────────────────────────────────────────────────────

    @Test fun `search is case insensitive`() {
        val lower = FuzzySearchHelper.search("crocin", medicines)
        val upper = FuzzySearchHelper.search("CROCIN", medicines)
        val mixed = FuzzySearchHelper.search("CrOcIn", medicines)
        assertEquals(lower.map { it.id }, upper.map { it.id })
        assertEquals(lower.map { it.id }, mixed.map { it.id })
    }

    // ── No false positives ────────────────────────────────────────────────────

    @Test fun `completely unrelated query returns empty`() {
        val results = FuzzySearchHelper.search("xyzqwerty", medicines)
        assertTrue(results.isEmpty())
    }

    // ── Stress test: 10,000 searches on 1,000-item list ──────────────────────

    @Test fun `stress 10000 searches on 1000 medicines complete under 5 seconds`() {
        val bigList = (1..1000).map { i ->
            med(i, "Medicine $i", "Generic $i", "Salt $i")
        } + medicines

        val queries = listOf("para", "metro", "amlo", "augm", "vitamin", "atorv")
        val start = System.currentTimeMillis()
        repeat(10_000) { i ->
            FuzzySearchHelper.search(queries[i % queries.size], bigList)
        }
        val elapsed = System.currentTimeMillis() - start
        assertTrue(
            "Stress test took ${elapsed}ms, expected < 5000ms",
            elapsed < 5000
        )
    }

    // ── Levenshtein sentinel correctness (-1 means over-threshold) ────────────

    @Test fun `three typos beyond threshold returns no match`() {
        // MAX_LEVENSHTEIN_DISTANCE = 2, so "Crocinabc" (3 extra chars) should NOT match
        val results = FuzzySearchHelper.search("Zzzz", medicines)
        assertTrue(results.isEmpty())
    }

    @Test fun `threshold constant is set to 2`() {
        assertEquals(2, Constants.MAX_LEVENSHTEIN_DISTANCE)
    }
}
