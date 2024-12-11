package com.uwu.zooapi.service

import com.uwu.zooapi.dto.Animal
import com.uwu.zooapi.entity.AnimalEntity
import com.uwu.zooapi.entity.EnclosureEntity
import com.uwu.zooapi.repository.AnimalRepository
import com.uwu.zooapi.repository.EnclosureRepository
import com.uwu.zooapi.util.convertToAnimalEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val enclosureRepository: EnclosureRepository
) {
    private val logger = LoggerFactory.getLogger(AnimalService::class.java)

    fun getFullAnimalsInfo() = animalRepository.findAll()

    fun getAllEnclosures() = enclosureRepository.findAll()

    fun getFullAnimalsInfoByEnclosure(id: Int): List<AnimalEntity> = animalRepository.findAllByEnclosure(checkEnclosure(id))

    fun updateAnimalInfo(request: Animal): AnimalEntity = animalRepository.save(request.convertToAnimalEntity(checkEnclosure(request.enclosure)))

    @Transactional
    fun deleteAnimalById(id: Int) = animalRepository.deleteById(id)

    private fun checkEnclosure(id: Int): EnclosureEntity {
        val enclosure = enclosureRepository.findById(id)
        if (!enclosure.isPresent) {
            logger.warn("Данного вольера не существует")
            throw Exception("Данного вольера не существует")
        } else return enclosure.get()
    }

}