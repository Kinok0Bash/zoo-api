package com.uwu.zooapi.controller

import com.uwu.zooapi.dto.request.BuyTicketRequest
import com.uwu.zooapi.entity.TicketEntity
import com.uwu.zooapi.service.TicketService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tickets")
@Tag(
    name = "Билеты",
    description = "Контроллер для управления билетами"
)
class TicketController(private val ticketService: TicketService) {
    private val logger = LoggerFactory.getLogger(TicketController::class.java)

    @PostMapping("/buy")
    @Operation(summary = "Покупка билета")
    fun buyTicket(@RequestBody request: BuyTicketRequest): ResponseEntity<Int> {
        logger.info("Запрос на покупку билета")
        return ResponseEntity.ok(ticketService.buyTicket(request))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение билета по id")
    fun getTicketById(@PathVariable id: Int): ResponseEntity<TicketEntity> {
        logger.info("Запрос на получение билета по id")
        return ResponseEntity.ok(ticketService.getTicketById(id))
    }

    @GetMapping("/visit")
    @Operation(summary = "Проставление посещения по билету")
    fun visitTicket(@RequestParam id: Int): ResponseEntity<Int> {
        logger.info("Запрос на проставление посещения по билету")
        return ResponseEntity.ok(ticketService.visitTicket(id))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}