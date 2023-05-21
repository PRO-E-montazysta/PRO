package com.example.e_montazysta.di

import androidx.lifecycle.ViewModelProvider
import com.example.e_montazysta.data.repository.interfaces.IToolRepository

class ToolsViewModelFactory(private val repository: IToolRepository) : ViewModelProvider.Factory