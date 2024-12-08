package com.uwu.zooapi.service

import com.uwu.zooapi.dao.ReportDAO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val reportDAO: ReportDAO
) {
    private val logger = LoggerFactory.getLogger(ReportService::class.java)

    fun generateAnimalTxtReport(): ByteArray {
        val data = reportDAO.getAnimalReport()

        val header = "Список животных в загонах на декабрь 2024 года\n\n" +
                "Название загона                         | Имя животного    | Вид                 | Дата рождения | Дата прибытия\n" +
                "-------------------------------------------------------------------------------------------------------------------\n"

        val body = data.forEach { element ->
            String.format(
                "%-40s| %-17s| %-20s| %-14s| %-13s",
                element.enclosure,
                element.animalName,
                element.animalSpecies,
                element.dateOfBirth,
                element.animalName
            )
        }

        logger.info("Отчет сформирован успешно")
        return (header + body).toByteArray(Charsets.UTF_8)
    }

    fun generateMedicalTxtReport(month: Int, year: Int): ByteArray {
        val data = reportDAO.getMedicalReport(month, year)

        val header = "Отчет о медицинских осмотрах за $month/$year\n\n" +
                "Имя животного    | Дата осмотра  | Диагноз                                 | Назначенное лечение           | ФИО сотрудника\n" +
                "--------------------------------------------------------------------------------------------------------------------------------------\n"

        val body = data.forEach { element ->
            String.format(
                "%-17s| %-14s| %-40s| %-30s| %-20s",
                element.animalName,
                element.checkupDate,
                element.diagnosis,
                element.treatment,
                element.staff
            )
        }

        logger.info("Отчет сформирован успешно")
        return (header + body).toByteArray(Charsets.UTF_8)
    }

    fun generateTicketSalesTxtReport(month: Int, year: Int): ByteArray {
        val data = reportDAO.getTicketSalesReport(month, year)

        val header = "Продажи билетов за $month/$year\n\n" +
                "Имя посетителя                          | Тип билета  | Цена        | Дата покупки\n" +
                "--------------------------------------------------------------------------------------\n"

        val body = data.forEach { element ->
            String.format(
                "%-40s| %-12s| %-12s| %-13s",
                element.name,
                element.ticketType,
                element.price,
                element.purchaseDate
            )
        }

        logger.info("Отчет сформирован успешно")
        return (header + body).toByteArray(Charsets.UTF_8)
    }
}
