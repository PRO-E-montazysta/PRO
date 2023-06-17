package com.example.e_montazysta.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.ui.stage.StageDAO
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
            val releaseDAOs = releaseService.getToolRelease(token)
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

    override suspend fun createRelease(items: List<ReleaseItem>, stageId: Int, warehouseId: Int?): Result<List<StageDAO>> {
        return try {
            val releaseService = serviceProvider.getReleaseService()
            val elements = items.filter { it.isElement }.map { it.mapToElementReleaseRequest() }
            val tools = items.filter { !it.isElement }.map { it.mapToToolReleaseRequest() }

            val releaseDAOs = mutableListOf<StageDAO>()

            if (elements.isNotEmpty()) {
                val elementReleaseDAOs = releaseService.createElementsRelease(token, stageId, warehouseId!!, elements)
                releaseDAOs.add(elementReleaseDAOs)
            }
            if (tools.isNotEmpty()) {
                val toolsReleaseDAOs = releaseService.createToolsRelease(token, stageId, tools)
                releaseDAOs.add(toolsReleaseDAOs)
                }
            Result.Success(releaseDAOs)
        } catch (e: Exception) {
            Log.e(TAG, e.message!! )
            Result.Error(e)
        }
    }

    override suspend fun createReturn(
        items: List<ReleaseItem>,
        stageId: Int,
        warehouseId: Int?
    ): Result<List<StageDAO>> {
        return try {
            val releaseService = serviceProvider.getReleaseService()
            val elements = items.filter { it.isElement }.map { it.mapToElementReleaseRequest() }
            val tools = items.filter { !it.isElement }.map { it.mapToToolReleaseRequest() }

            val releaseDAOs = mutableListOf<StageDAO>()

            if (elements.isNotEmpty()) {
                val elementReleaseDAOs =
                    releaseService.createElementsReturn(token, stageId, warehouseId!!, elements)
                releaseDAOs.add(elementReleaseDAOs)
            }
            if (tools.isNotEmpty()) {
                val toolsReleaseDAOs = releaseService.createToolsReturn(token, stageId, tools)
                releaseDAOs.add(toolsReleaseDAOs)
            }
            Result.Success(releaseDAOs)
        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
            Result.Error(e)
        }
    }
}
