package com.example.ordernotifier.data

import OrderDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrderInfo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}

