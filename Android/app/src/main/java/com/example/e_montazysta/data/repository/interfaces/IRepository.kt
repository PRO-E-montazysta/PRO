package com.example.e_montazysta.data.repository.interfaces

interface IRepository<T> {
    fun getDetails(id: Long)
    fun getFilter(entity: T)
    fun update(entity: T)
    fun create(entity: T)
    fun delete(entity: T)
}