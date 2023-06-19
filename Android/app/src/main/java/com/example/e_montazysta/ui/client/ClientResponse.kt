package com.example.e_montazysta.ui.client

import android.content.ContentValues
import android.util.Log
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IClientRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


data class ClientDAO(
    val companyId: Int,
    val contactDetails: String,
    val deleted: Boolean,
    val id: Int,
    val name: String,
    val orders: List<Int>
){
    companion object : KoinComponent {
        val clientRepository: IClientRepository by inject()

        suspend fun getClient(clientId: Int): ClientDAO? {
            return when (val result = clientRepository.getClient(clientId)) {
                is Result.Success -> result.data
                is Result.Error -> {
                    Log.e(ContentValues.TAG, Log.getStackTraceString(result.exception))
                    null
                }
            }
        }
    }
}