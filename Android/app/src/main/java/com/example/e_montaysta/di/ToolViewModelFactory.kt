package com.example.e_montaysta.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_montaysta.data.repository.ToolRepository
import com.example.e_montaysta.ui.viewmodels.ToolViewModel

/**
 * ViewModel provider factory to instantiate ToolViewModel.
 * Required given ToolViewModel has a non-empty constructor
 */
class ToolViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToolViewModel::class.java)) {
            return ToolViewModel(
                toolRepository = ToolRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}