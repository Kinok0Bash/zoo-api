package com.uwu.zooapi.service

import com.uwu.zooapi.dto.request.BuyTicketRequest
import com.uwu.zooapi.entity.TicketEntity
import com.uwu.zooapi.entity.VisitorEntity
import com.uwu.zooapi.repository.TicketRepository
import com.uwu.zooapi.repository.VisitorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
    private val visitorRepository: VisitorRepository
) {
    private val logger = LoggerFactory.getLogger(TicketService::class.java)

    fun buyTicket(request: BuyTicketRequest): Int {
        var visitorEntity = VisitorEntity(
            name = request.name,
            visitDate = LocalDate.now(),
            feedback = null
        )
        visitorEntity = visitorRepository.save(visitorEntity)

        val ticket = TicketEntity(
            visitor = visitorEntity,
            ticketType = request.ticketType,
            price = BigDecimal.valueOf(request.price),
            purchaseDate = LocalDate.now()
        )
        return ticketRepository.save(ticket).id
    }

    fun getTicketById(id: Int) = checkTicket(ticketRepository.findById(id))

    fun visitTicket(id: Int) = checkTicket(ticketRepository.findById(id))

    private fun checkTicket(ticket: Optional<TicketEntity>): TicketEntity {
        if (!ticket.isPresent) {
            logger.warn("Данного билета не существует")
            throw Exception("Данного билета не существует")
        } else return ticket.get()
    }

}