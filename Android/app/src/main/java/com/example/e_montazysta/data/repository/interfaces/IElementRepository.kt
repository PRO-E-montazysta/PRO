package com.example.e_montazysta.data.repository.interfaces

import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Result

interface IElementRepository {
    suspend fun getElements() : Result<List<Element>>
    suspend fun getElementByCode(code: String?) : Result<Element>
    suspend fun getElementDetails(id: Int) : Result<Element>
}