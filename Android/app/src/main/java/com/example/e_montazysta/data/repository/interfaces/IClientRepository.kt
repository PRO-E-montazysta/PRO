package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.ui.client.ClientDAO

interface IClientRepository {
    suspend fun getClient(id: Int): Result<ClientDAO>
}
