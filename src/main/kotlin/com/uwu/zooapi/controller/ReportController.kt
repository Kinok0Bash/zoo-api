package com.uwu.zooapi.controller

import com.uwu.zooapi.dao.ReportDAO
import com.uwu.zooapi.dto.report.AnimalReport
import com.uwu.zooapi.dto.report.MedicalReport
import com.uwu.zooapi.dto.report.TicketSalesReport
import com.uwu.zooapi.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports")
@Tag(
    name = "Отчёты",
    description = "Контроллер для выкачки отчетов"
)
class ReportController(
    private val reportService: ReportService,
    private val reportDAO: ReportDAO
) {
    private val logger = LoggerFactory.getLogger(ReportController::class.java)

    @GetMapping("/animals")
    @Operation(summary = "Получение отчета обо всех животных")
    fun getAnimalReport(): ResponseEntity<MutableList<AnimalReport>> {
        logger.info("Запрос на получение отчета обо всех животных")
        return ResponseEntity.ok(reportDAO.getAnimalReport())
    }

    @GetMapping("/animals/download")
    @Operation(summary = "Выкачка отчета обо всех животных")
    fun getAnimalTxtReport(): ResponseEntity<ByteArray> {
        logger.info("Запрос на скачивание отчета обо всех животных")

        val txt = reportService.generateAnimalTxtReport()
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=animal_report.txt")
        headers.add("Content-Type", "text/plain; charset=UTF-8")

        return ResponseEntity(txt, headers, HttpStatus.OK)
    }

    @GetMapping("/medical")
    @Operation(summary = "Получение отчета о здоровье животных за определенный год и месяц")
    fun getMedicalReport(
        @RequestParam("month") month: Int,
        @RequestParam("year") year: Int
    ): ResponseEntity<MutableList<MedicalReport>> {
        logger.info("Запрос на получение отчета о здоровье животных за $month месяц $year года")
        return ResponseEntity.ok(reportDAO.getMedicalReport(month, year))
    }

    @GetMapping("/medical/download")
    @Operation(summary = "Выкачка отчета о здоровье животных за определенный год и месяц")
    fun getMedicalTxtReport(
        @RequestParam("month") month: Int,
        @RequestParam("year") year: Int
    ): ResponseEntity<ByteArray> {
        logger.info("Запрос на скачивание отчета о здоровье животных за $month месяц $year года")

        val txt = reportService.generateMedicalTxtReport(month, year)
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=medical_report_${year}_${month}.txt")
        headers.add("Content-Type", "text/plain; charset=UTF-8")

        return ResponseEntity(txt, headers, HttpStatus.OK)
    }

    @GetMapping("/tickets")
    @Operation(summary = "Получение отчета о продаже билетов за определенный год и месяц")
    fun getTicketSalesReport(
        @RequestParam("month") month: Int,
        @RequestParam("year") year: Int
    ): ResponseEntity<MutableList<TicketSalesReport>> {
        logger.info("Запрос на получение отчета о продаже билетов за $month месяц $year года")
        return ResponseEntity.ok(reportDAO.getTicketSalesReport(month, year))
    }

    @GetMapping("/tickets/download")
    @Operation(summary = "Выкачка отчета о продаже билетов за определенный год и месяц")
    fun getTicketSalesTxtReport(
        @RequestParam("month") month: Int,
        @RequestParam("year") year: Int
    ): ResponseEntity<ByteArray> {
        logger.info("Запрос на скачивание отчета о продаже билетов за $month месяц $year года")

        val txt = reportService.generateTicketSalesTxtReport(month, year)
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=ticket_sales_report_${year}_${month}.txt")
        headers.add("Content-Type", "text/plain; charset=UTF-8")

        return ResponseEntity(txt, headers, HttpStatus.OK)
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }
}