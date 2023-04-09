package com.example.e_montaysta.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository

class ToolViewModel(private val toolRepository: IToolRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Tool Fragment"
    }
    val text: LiveData<String> = _text
}