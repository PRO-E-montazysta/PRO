package com.example.e_montaysta.data.repository.Interfaces

interface IRepository<T> {
    fun getDetails(id: Long)
    fun getFilter(entity: T)
    fun update(entity: T)
    fun create(entity: T)
    fun delete(entity: T)
}