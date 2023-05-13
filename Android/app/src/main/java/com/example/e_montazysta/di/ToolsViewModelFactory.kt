package com.example.e_montazysta.di

import androidx.lifecycle.ViewModelProvider
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository

class ToolsViewModelFactory(private val repository: IToolRepository) : ViewModelProvider.Factory