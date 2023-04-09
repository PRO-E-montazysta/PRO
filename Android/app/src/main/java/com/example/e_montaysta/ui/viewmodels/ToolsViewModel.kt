package com.example.e_montaysta.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import kotlinx.coroutines.launch

class ToolsViewModel(private val repository: IToolRepository) : ViewModel() {

    private val _tools = MutableLiveData<List<Tool>>()
    val tools: LiveData<List<Tool>>
        get() = _tools

    fun loadTools() {
        viewModelScope.launch {
            val response = repository.getTools()
            if (response.isSuccessful) {
                _tools.value = response.body()
            } else {
                Log.e("ToolViewModel", "Failed to load tools: ${response.code()}")
            }
        }
    }
}