package com.janaushadhi.finder.search

import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.utils.Constants
import org.apache.commons.text.similarity.LevenshteinDistance

/**
 * O(n × L) fuzzy search where n = medicine count, L = query length.
 * Exact substring match short-circuits before the more expensive Levenshtein pass.
 * Note: LevenshteinDistance.apply() returns -1 (not null) when distance > threshold.
 */
object FuzzySearchHelper {

    // Bounded Levenshtein — O(min(threshold, L)^2) per pair instead of O(L^2)
    private val levenshtein = LevenshteinDistance(Constants.MAX_LEVENSHTEIN_DISTANCE)

    fun search(query: String, medicines: List<Medicine>): List<Medicine> {
        if (query.length < Constants.MIN_QUERY_LENGTH) return emptyList()
        val q = query.lowercase().trim()
        return medicines.filter { med ->
            val brand   = med.brand.lowercase()
            val generic = med.generic.lowercase()
            val salt    = med.salt.lowercase()

            // Fast path: exact substring match — O(L) per field
            if (brand.contains(q) || generic.contains(q) || salt.contains(q)) return@filter true

            // Slow path: Levenshtein on prefix — only for queries ≥ 4 chars to reduce false positives
            // LevenshteinDistance.apply() returns -1 when distance exceeds threshold (NOT null)
            if (q.length >= 4) {
                val distBrand   = levenshtein.apply(q, brand.take(q.length))
                val distGeneric = levenshtein.apply(q, generic.take(q.length))
                // Treat -1 (over-threshold sentinel) as MAX so it never matches
                val minDist = when {
                    distBrand   != -1 && distGeneric != -1 -> minOf(distBrand, distGeneric)
                    distBrand   != -1                      -> distBrand
                    distGeneric != -1                      -> distGeneric
                    else                                   -> Int.MAX_VALUE
                }
                return@filter minDist <= Constants.MAX_LEVENSHTEIN_DISTANCE
            }
            false
        }.take(10)
    }
}
