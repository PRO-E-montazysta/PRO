package com.example.e_montaysta.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import com.example.e_montaysta.ui.viewmodels.ToolsViewModel

class ToolsViewModelFactory(private val repository: IToolRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToolsViewModel::class.java)) {
            return ToolsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}