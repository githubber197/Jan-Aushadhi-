package com.janaushadhi.finder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "my_meds")
data class MyMed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val refillDate: String,   // ISO date string yyyy-MM-dd
    val quantityPerMonth: Int = 30,
    val addedAt: Long = System.currentTimeMillis()
) {
    // Cached formatters — created once, not on every bind/call
    companion object {
        private val ISO_FMT   = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        private val LABEL_FMT = SimpleDateFormat("dd MMM", Locale.getDefault())
    }

    fun daysUntilRefill(): Int {
        return try {
            val refill = ISO_FMT.parse(refillDate)?.time ?: return 0
            val diff   = refill - System.currentTimeMillis()
            (diff / (1000L * 60 * 60 * 24)).toInt()
        } catch (e: Exception) { 0 }
    }

    fun refillStatusLabel(): String {
        val days = daysUntilRefill()
        return when {
            days < 0  -> "Overdue ${-days}d"
            days == 0 -> "Due Today!"
            days <= 2 -> "Due in ${days}d"
            else      -> try {
                LABEL_FMT.format(ISO_FMT.parse(refillDate)!!)
            } catch (e: Exception) { refillDate }
        }
    }

    fun isUrgent(): Boolean = daysUntilRefill() <= 2
}
