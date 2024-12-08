package com.uwu.zooapi.repository

import com.uwu.zooapi.entity.EnclosureEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EnclosureRepository: CrudRepository<EnclosureEntity, Int>