package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.Interfaces.IReleaseRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReleaseRepository (
    private val serviceProvider: IServiceProvider
) : IReleaseRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    override suspend fun getRelease(): Result<List<Release>> {
        return try {
            val releaseService = serviceProvider.getReleaseService()
            val releaseDAOs = releaseService.getRelease(token)
            val releases = releaseDAOs.map { it.mapToRelease() }
            Result.Success(releases)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getReleaseDetail(id: Int): Result<Release> {
        return try {
            val releaseService = serviceProvider.getReleaseService()
            val releaseDAO = releaseService.getReleaseDetail(token, id)
            val releaseDetail = releaseDAO.mapToRelease()
            Result.Success(releaseDetail)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}