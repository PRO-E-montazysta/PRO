package com.example.e_montazysta.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import com.example.e_montazysta.ui.viewmodels.ToolsViewModel

class ToolsViewModelFactory(private val repository: IToolRepository) : ViewModelProvider.Factory {


}