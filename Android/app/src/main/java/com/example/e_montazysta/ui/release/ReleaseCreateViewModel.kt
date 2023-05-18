package com.example.e_montazysta.ui.release

import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ReleaseCreateViewModel() : ViewModel(), CoroutineScope {



    fun addElementToRelease(code: String?) : String?  {
        return code
    }
    fun addToolToRelease(code: String?) : String? {
        return code
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}