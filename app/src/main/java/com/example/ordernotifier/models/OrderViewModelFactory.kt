package com.example.ordernotifier.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = OrderViewModel()
        viewModel.initializeDatabase(context)
        return viewModel as T
    }
}
