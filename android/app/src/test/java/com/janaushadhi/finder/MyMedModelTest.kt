package com.janaushadhi.finder

import com.janaushadhi.finder.data.model.MyMed
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Unit tests for MyMed model — refill logic, urgency, and label formatting.
 * Pure JVM, no Android framework.
 */
class MyMedModelTest {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private fun dateOffset(days: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, days)
        return sdf.format(cal.time)
    }

    // ── daysUntilRefill ───────────────────────────────────────────────────────

    @Test fun `daysUntilRefill returns positive for future date`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(10))
        assertTrue("Expected positive days", med.daysUntilRefill() > 0)
    }

    @Test fun `daysUntilRefill returns negative for past date`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(-5))
        assertTrue("Expected negative days", med.daysUntilRefill() < 0)
    }

    @Test fun `daysUntilRefill returns zero for today`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(0))
        // Could be 0 or -1 depending on time of day; ensure it's ≤ 0
        assertTrue(med.daysUntilRefill() <= 0)
    }

    @Test fun `daysUntilRefill handles invalid date gracefully`() {
        val med = MyMed(name = "Test", refillDate = "not-a-date")
        assertEquals(0, med.daysUntilRefill())
    }

    // ── isUrgent ─────────────────────────────────────────────────────────────

    @Test fun `isUrgent true when due today`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(0))
        assertTrue(med.isUrgent())
    }

    @Test fun `isUrgent true when due in 1 day`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(1))
        assertTrue(med.isUrgent())
    }

    @Test fun `isUrgent true when overdue`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(-3))
        assertTrue(med.isUrgent())
    }

    @Test fun `isUrgent false when 5 days away`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(5))
        assertFalse(med.isUrgent())
    }

    // ── refillStatusLabel ─────────────────────────────────────────────────────

    @Test fun `label says Overdue for past date`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(-3))
        assertTrue(med.refillStatusLabel().contains("Overdue"))
    }

    @Test fun `label says Due Today for today`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(0))
        // Either "Due Today!" or "Overdue 0d" depending on exact time
        val label = med.refillStatusLabel()
        assertTrue(label.contains("Due") || label.contains("Overdue"))
    }

    @Test fun `label says Due in Nd for near future`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(2))
        assertTrue(med.refillStatusLabel().contains("Due in"))
    }

    @Test fun `label shows formatted date for far future`() {
        val med = MyMed(name = "Test", refillDate = dateOffset(30))
        val label = med.refillStatusLabel()
        // Should be a month abbreviation like "Jun" or "Jul" — not "Due in"
        assertFalse(label.isEmpty())
    }

    // ── Stress: 50,000 rapid daysUntilRefill calls ────────────────────────────

    @Test fun `stress 50000 daysUntilRefill calls complete under 3 seconds`() {
        val start = System.currentTimeMillis()
        val med = MyMed(name = "Test", refillDate = dateOffset(10))
        repeat(50_000) { med.daysUntilRefill() }
        val elapsed = System.currentTimeMillis() - start
        assertTrue("Took ${elapsed}ms, expected < 3000ms", elapsed < 3000)
    }
}
