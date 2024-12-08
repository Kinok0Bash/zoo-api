package com.uwu.zooapi.service

import com.uwu.zooapi.entity.AnimalEntity
import com.uwu.zooapi.repository.AnimalRepository
import com.uwu.zooapi.repository.EnclosureRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val enclosureRepository: EnclosureRepository
) {
    private val logger = LoggerFactory.getLogger(AnimalService::class.java)

    fun getFullAnimalsInfo() = animalRepository.findAll()

    fun getFullAnimalsInfoByEnclosure(id: Int): List<AnimalEntity> {
        val enclosure = enclosureRepository.findById(id)
        if (!enclosure.isPresent) {
            logger.info("Данного вольера не существует")
            throw Exception("Данного вольера не существует")
        }
        return animalRepository.findAllByEnclosure(enclosure.get())
    }

    fun updateAnimalInfo(animal: AnimalEntity) = animalRepository.save(animal)

    fun deleteAnimalById(id: Int) = animalRepository.deleteById(id)

}