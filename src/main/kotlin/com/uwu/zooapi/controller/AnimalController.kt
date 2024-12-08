package com.uwu.zooapi.controller

import com.uwu.zooapi.entity.AnimalEntity
import com.uwu.zooapi.entity.EnclosureEntity
import com.uwu.zooapi.service.AnimalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/animals")
@Tag(
    name = "Животные",
    description = "Контроллер для управления животными"
)
class AnimalController(
    private val animalService: AnimalService
) {
    private val logger = LoggerFactory.getLogger(AnimalController::class.java)

    @GetMapping
    @Operation(summary = "Получение информации обо всех животных")
    fun getFullAnimalsInfo(): ResponseEntity<List<AnimalEntity>> {
        logger.info("Запрос на получение всей информации обо всех животных")
        return ResponseEntity.ok(animalService.getFullAnimalsInfo())
    }

    @GetMapping("/enclosures")
    @Operation(summary = "Получение списка вольеров")
    fun getAllEnclosures(): ResponseEntity<List<EnclosureEntity>> {
        logger.info("Запрос на получение всех вольеров")
        return ResponseEntity.ok(animalService.getAllEnclosures())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации обо всех животных в вольере по id вольера")
    fun getFullAnimalsInfoByEnclosure(@PathVariable id: Int): ResponseEntity<List<AnimalEntity>> {
        logger.info("Запрос на получение информации обо всех животных в вольере $id")
        return ResponseEntity.ok(animalService.getFullAnimalsInfoByEnclosure(id))
    }

    @PutMapping
    @Operation(summary = "Обновление информации о животном")
    fun updateAnimalInfo(@RequestBody animal: AnimalEntity): ResponseEntity<Map<String, String>> {
        logger.info("Запрос на обновление информации о животном с id ${animal.id}")
        animalService.updateAnimalInfo(animal)
        return ResponseEntity.ok(mapOf("message" to "Успешно!"))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление животного по id")
    fun deleteAnimalById(@PathVariable id: Int): ResponseEntity<Map<String, String>> {
        logger.info("Запрос на удаление животного с id $id")
        animalService.deleteAnimalById(id)
        return ResponseEntity.ok(mapOf("message" to "Успешно!"))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}