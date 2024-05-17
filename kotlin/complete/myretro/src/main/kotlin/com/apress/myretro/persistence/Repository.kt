package com.apress.myretro.persistence

interface Repository <D,ID> {
    fun save(domain:D):D
    fun findById(id: ID): D?
    fun findAll():Iterable<D>
    fun delete(id:ID)
}