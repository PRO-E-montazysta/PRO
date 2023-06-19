package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IClientRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.client.ClientDAO
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClientRepository(private val serviceProvider: IServiceProvider) : IClientRepository,
    KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    val locationService = serviceProvider.getClientService()

    override suspend fun getClient(id: Int): Result<ClientDAO> {
        return try {
            val result = locationService.getClient(token, id)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}