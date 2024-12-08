package com.uwu.zooapi.dao

import com.uwu.zooapi.dto.report.AnimalReport
import com.uwu.zooapi.dto.report.MedicalReport
import com.uwu.zooapi.dto.report.TicketSalesReport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ReportDAO(private val jdbcTemplate: JdbcTemplate) {

    private val animalReportQuery = """
        SELECT 
                e.description AS "Название загона",
                a.name AS "Имя животного",
                a.species AS "Вид",
                a.date_of_birth AS "Дата рождения",
                a.arrival_date AS "Дата прибытия"
            FROM Animals a
            JOIN Enclosures e ON a.enclosure_id = e.enclosure_id
        """.trimIndent()

    private val medicalReportQuery = """
            SELECT 
                a.name AS "Имя животного",
                m.checkup_date AS "Дата осмотра",
                m.diagnosis AS "Диагноз",
                m.treatment AS "Назначенное лечение",
                CONCAT(s.first_name, ' ', s.last_name) AS "ФИО сотрудника"
            FROM Medical_checkups m
            JOIN Animals a ON m.animal_id = a.animal_id
            JOIN Staff s ON m.staff_id = s.staff_id
            WHERE EXTRACT(MONTH FROM m.checkup_date) = ? 
              AND EXTRACT(YEAR FROM m.checkup_date) = ?
        """

    private val ticketSalesReportQuery = """
            SELECT 
                v.name AS "Имя посетителя",
                t.ticket_type AS "Тип билета",
                t.price AS "Цена",
                t.purchase_date AS "Дата покупки"
            FROM Tickets t
            JOIN Visitors v ON t.visitor_id = v.visitor_id
            WHERE EXTRACT(MONTH FROM t.purchase_date) = ?
              AND EXTRACT(YEAR FROM t.purchase_date) = ?
        """

    fun getAnimalReport(): MutableList<AnimalReport> {
        val data = jdbcTemplate.queryForList(animalReportQuery)
        val report = mutableListOf<AnimalReport>()
        data.forEach { element ->
            report.add(
                AnimalReport(
                    enclosure = element["Название загона"] as String,
                    animalName = element["Имя животного"] as String,
                    animalSpecies = element["Вид"] as String,
                    dateOfBirth = element["Дата рождения"] as String,
                    arrivalDate = element["Дата прибытия"] as String,
                )
            )
        }
        return report
    }

    fun getMedicalReport(month: Int, year: Int): MutableList<MedicalReport> {
        val data = jdbcTemplate.queryForList(medicalReportQuery, month, year)
        val report = mutableListOf<MedicalReport>()
        data.forEach { element ->
            report.add(
                MedicalReport(
                    animalName = element["Имя животного"] as String,
                    checkupDate = element["Дата осмотра"] as String,
                    diagnosis = element["Диагноз"] as String,
                    treatment = element["Назначенное лечение"] as String,
                    staff = element["ФИО сотрудника"] as String
                )
            )
        }
        return report
    }

    fun getTicketSalesReport(month: Int, year: Int): MutableList<TicketSalesReport> {
        val data = jdbcTemplate.queryForList(ticketSalesReportQuery, month, year)
        val report = mutableListOf<TicketSalesReport>()
        data.forEach { element ->
            report.add(
                TicketSalesReport(
                    name = element["Имя посетителя"] as String,
                    ticketType = element["Тип билета"] as String,
                    price = element["Цена"] as String,
                    purchaseDate = element["Дата покупки"] as String
                )
            )
        }
        return report
    }

}