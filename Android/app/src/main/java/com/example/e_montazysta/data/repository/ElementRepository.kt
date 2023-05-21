package com.example.e_montazysta.data.repository

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import com.example.e_montazysta.data.services.IServiceProvider
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ElementRepository(
    private val serviceProvider: IServiceProvider
) : IElementRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private val token = "Bearer " + sharedPreferencesHelper.get("lama").toString()
    override suspend fun getElements(): Result<List<Element>> {
        return try {
            val elementService = serviceProvider.getElementService()
            val elementDAOs = elementService.getElements(token)
            val elements = elementDAOs.map { it.mapToElement() }
            Result.Success(elements)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getElementByCode(code: String?): Result<Element> {
        return try {
            val elementService = serviceProvider.getElementService()
            val elementDAO = elementService.getElementByCode(token, code)
            Result.Success(elementDAO.mapToElement())
        } catch (e: Exception) {
            Result.Error(e)
            throw e
        }
    }
}