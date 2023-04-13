package com.example.e_montazysta.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.data.repository.Interfaces.IToolRepository
import org.koin.dsl.module


val viewModelModule = module {
    factory { ToolViewModel(get()) }
}

class ToolViewModel(
    private val toolRepository: IToolRepository
    ) : ViewModel() {

val tool: LiveData<Tool> = liveData {
}

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is Tool Fragment"
//    }
//    val text: LiveData<String> = _text
}