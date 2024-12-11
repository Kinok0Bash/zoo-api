package com.uwu.zooapi.repository

import com.uwu.zooapi.entity.VisitorEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VisitorRepository: CrudRepository<VisitorEntity, Int>