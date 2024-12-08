package com.uwu.zooapi.dao

import com.uwu.zooapi.dto.report.AnimalReport
import com.uwu.zooapi.dto.report.MedicalReport
import com.uwu.zooapi.dto.report.TicketSalesReport
import com.uwu.zooapi.enum.TicketType
import com.uwu.zooapi.enum.value
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
                    enclosure = element["Название загона"].toString(),
                    animalName = element["Имя животного"].toString(),
                    animalSpecies = element["Вид"].toString(),
                    dateOfBirth = element["Дата рождения"].toString(),
                    arrivalDate = element["Дата прибытия"].toString(),
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
                    animalName = element["Имя животного"].toString(),
                    checkupDate = element["Дата осмотра"].toString(),
                    diagnosis = element["Диагноз"].toString(),
                    treatment = element["Назначенное лечение"].toString(),
                    staff = element["ФИО сотрудника"].toString()
                )
            )
        }
        return report
    }

    fun getTicketSalesReport(month: Int, year: Int): MutableList<TicketSalesReport> {
        val data = jdbcTemplate.queryForList(ticketSalesReportQuery, month, year)
        val report = mutableListOf<TicketSalesReport>()
        data.forEach { element ->
            var elementTicketType = ""

            elementTicketType = when(element["Тип билета"]) {
                TicketType.FAMILY -> TicketType.FAMILY.value()
                TicketType.ADULT -> TicketType.ADULT.value()
                TicketType.CHILD -> TicketType.CHILD.value()
                else -> element["Тип билета"].toString()
            }

            report.add(
                TicketSalesReport(
                    name = element["Имя посетителя"].toString(),
                    ticketType = elementTicketType,
                    price = element["Цена"].toString(),
                    purchaseDate = element["Дата покупки"].toString()
                )
            )
        }
        return report
    }

}