package com.janaushadhi.finder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey val id: Int = 0,
    val brand: String,
    val generic: String,
    val salt: String,
    val manufacturer: String,
    val brand_price: Double,
    val generic_price: Double,
    val category: String,
    val icon: String = "💊",
    val uses: String = "",
    val description: String = "",
    val side_effects: String = ""
) {
    val savingsAmount: Double get() = brand_price - generic_price
    val savingsPercent: Int get() = if (brand_price > 0) ((savingsAmount / brand_price) * 100).toInt() else 0
    val monthlySaving: Double get() = savingsAmount * 2
    val annualSaving: Double get() = monthlySaving * 12
}
