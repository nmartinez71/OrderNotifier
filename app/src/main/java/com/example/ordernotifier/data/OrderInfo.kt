package com.example.ordernotifier.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderInfo(
    @PrimaryKey val orderNumber: String,
    val createdAt: Long,
    val durationSeconds: Long
) {
    val timeRemaining: Long
        get() {
            val elapsed = (System.currentTimeMillis() - createdAt) / 1000
            return (durationSeconds - elapsed).coerceAtLeast(0)
        }
}
