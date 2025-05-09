package com.example.ordernotifier.models

import OrderDao
import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordernotifier.data.OrderInfo
import kotlinx.coroutines.launch
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateMapOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.ordernotifier.data.AppDatabase
import kotlinx.coroutines.delay

open class OrderViewModel : ViewModel() {
    private lateinit var db: AppDatabase
    private lateinit var orderDao: OrderDao
    val orders = mutableStateListOf<OrderInfo>()

    private val orderTimers = mutableStateMapOf<String, Long>()

    init {
        loadOrders()
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "order_notification_channel",
                "Order Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for new orders"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun initializeDatabase(context: Context) {
        if (!::db.isInitialized) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "order-database"
            ).build()
            orderDao = db.orderDao()
            loadOrders()
        }
    }

    private fun loadOrders() {
        viewModelScope.launch {
            if (::orderDao.isInitialized) {
                orders.clear()
                val loadedOrders = orderDao.getAllOrders()
                orders.addAll(loadedOrders)
                loadedOrders.forEach { order ->
                    orderTimers[order.orderNumber.toString()] = order.timeRemaining
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    open fun addOrder(orderNumber: String, context: Context) {
        val now = System.currentTimeMillis()
        val order = OrderInfo(orderNumber, createdAt = now, durationSeconds = 100) //change duration in seconds to make wait longer

        orders.add(order)
        viewModelScope.launch {
            if (::orderDao.isInitialized) {
                orderDao.insertOrder(order)
            }
        }

        createNotificationChannel(context)
        triggerOrderNotification(order, context)

        startCountdown(order)
    }

    private fun startCountdown(order: OrderInfo) {
        viewModelScope.launch {
            while (order.timeRemaining > 0) {
                delay(1000)
                orderTimers[order.orderNumber.toString()] = order.timeRemaining
            }
        }
    }

    fun removeOrder(order: OrderInfo) {
        orders.remove(order)
        viewModelScope.launch {
            if (::orderDao.isInitialized) {
                orderDao.deleteOrder(order)
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun triggerOrderNotification(order: OrderInfo, context: Context) {
        val notificationId = 1
        val notificationManager = NotificationManagerCompat.from(context)

        val notification = NotificationCompat.Builder(context, "order_notification_channel")
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("New Order Added")
            .setContentText("Order ${order.orderNumber} added with ${order.durationSeconds / 60} minutes.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)
    }

}







