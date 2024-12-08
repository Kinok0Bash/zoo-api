package com.uwu.zooapi.repository

import com.uwu.zooapi.entity.AnimalEntity
import com.uwu.zooapi.entity.EnclosureEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepository: CrudRepository<AnimalEntity, Int> {
    override fun findAll(): List<AnimalEntity>
    fun findAllByEnclosure(enclosure: EnclosureEntity): List<AnimalEntity>
}