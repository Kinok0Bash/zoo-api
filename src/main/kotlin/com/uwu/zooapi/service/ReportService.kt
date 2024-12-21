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
                "Название загона                         | Имя животного    | Вид                 | Дата рождения | Дата прибытия\n | Самое старое животное в загоне" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------------\n"

        var body = ""
        data.forEach { element ->
            body += String.format(
                "%-40s| %-17s| %-20s| %-14s| %-13s | %s",
                element.enclosure,
                element.animalName,
                element.animalSpecies,
                element.dateOfBirth,
                element.animalName,
                element.oldestAnimal
            ) + "\n"
        }

        logger.info("Отчет сформирован успешно")
        return (header + body).toByteArray(Charsets.UTF_8)
    }

    fun generateMedicalTxtReport(month: Int, year: Int): ByteArray {
        val data = reportDAO.getMedicalReport(month, year)

        val header = "Отчет о медицинских осмотрах за $month/$year\n\n" +
                "Имя животного    | Дата осмотра  | Диагноз                                 | Назначенное лечение           | ФИО сотрудника               | Количество осмотров\n" +
                "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"

        var body = ""
        data.forEach { element ->
            body += String.format(
                "%-17s| %-14s| %-40s| %-30s| %-20s | %-4s",
                element.animalName,
                element.checkupDate,
                element.diagnosis,
                element.treatment,
                element.staff,
                element.checkupCount
            ) + "\n"
        }

        logger.info("Отчет сформирован успешно")
        return (header + body).toByteArray(Charsets.UTF_8)
    }

    fun generateTicketSalesTxtReport(month: Int, year: Int): ByteArray {
        val data = reportDAO.getTicketSalesReport(month, year)

        val header = "Продажи билетов за $month/$year\n\n" +
                "Имя посетителя                          | Тип билета  | Цена        | Дата покупки | Средняя цена билета для типа\n" +
                "--------------------------------------------------------------------------------------------------------------------------\n"

        var body = ""
        data.forEach { element ->
            body += String.format(
                "%-40s| %-12s| %-12s| %-13s | %-6s",
                element.name,
                element.ticketType,
                element.price,
                element.purchaseDate,
                element.avgPrice
            ) + "\n"
        }

        logger.info("Отчет сформирован успешно")
        return (header + body).toByteArray(Charsets.UTF_8)
    }
}
