package com.example.e_montazysta.data.repository.Interfaces

import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.Result

interface IReleaseRepository {
    suspend fun getRelease() : Result<List<Release>>
}
