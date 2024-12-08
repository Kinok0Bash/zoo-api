package com.uwu.zooapi.repository

import com.uwu.zooapi.entity.TicketEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository: CrudRepository<TicketEntity, Int>