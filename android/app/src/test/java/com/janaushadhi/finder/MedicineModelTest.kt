package com.janaushadhi.finder

import com.janaushadhi.finder.data.model.Medicine
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for Medicine model computed properties.
 * No Android framework needed — pure JVM.
 */
class MedicineModelTest {

    private fun medicine(brand: Double, generic: Double) = Medicine(
        id = 1, brand = "TestBrand", generic = "TestGeneric", salt = "TestSalt",
        manufacturer = "TestMfr", brand_price = brand, generic_price = generic,
        category = "pain"
    )

    // ── savingsAmount ─────────────────────────────────────────────────────────

    @Test fun `savingsAmount is brand minus generic`() {
        val med = medicine(brand = 100.0, generic = 20.0)
        assertEquals(80.0, med.savingsAmount, 0.001)
    }

    @Test fun `savingsAmount is zero when prices equal`() {
        val med = medicine(brand = 50.0, generic = 50.0)
        assertEquals(0.0, med.savingsAmount, 0.001)
    }

    @Test fun `savingsAmount handles zero generic price`() {
        val med = medicine(brand = 200.0, generic = 0.0)
        assertEquals(200.0, med.savingsAmount, 0.001)
    }

    // ── savingsPercent ────────────────────────────────────────────────────────

    @Test fun `savingsPercent is correct for 80 percent saving`() {
        val med = medicine(brand = 100.0, generic = 20.0)
        assertEquals(80, med.savingsPercent)
    }

    @Test fun `savingsPercent is zero when brand price is zero`() {
        val med = medicine(brand = 0.0, generic = 0.0)
        assertEquals(0, med.savingsPercent)
    }

    @Test fun `savingsPercent rounds down`() {
        // (333.0 - 100.0) / 333.0 = 69.96% → toInt() = 69
        val med = medicine(brand = 333.0, generic = 100.0)
        assertEquals(69, med.savingsPercent)
    }

    // ── monthlySaving / annualSaving ──────────────────────────────────────────

    @Test fun `monthlySaving is savingsAmount times 2`() {
        val med = medicine(brand = 100.0, generic = 20.0)
        assertEquals(160.0, med.monthlySaving, 0.001)
    }

    @Test fun `annualSaving is monthlySaving times 12`() {
        val med = medicine(brand = 100.0, generic = 20.0)
        assertEquals(1920.0, med.annualSaving, 0.001)
    }

    // ── Stress: large dataset of computations ─────────────────────────────────

    @Test fun `savingsPercent is correct across 10000 medicines`() {
        repeat(10_000) { i ->
            val brandPrice   = (i + 1).toDouble()
            val genericPrice = brandPrice * 0.2
            val med = medicine(brand = brandPrice, generic = genericPrice)
            assertEquals(80, med.savingsPercent)
        }
    }
}
