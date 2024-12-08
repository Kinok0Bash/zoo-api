package com.uwu.zooapi.util

import com.uwu.zooapi.dto.Animal
import com.uwu.zooapi.dto.User
import com.uwu.zooapi.entity.AnimalEntity
import com.uwu.zooapi.entity.EnclosureEntity
import com.uwu.zooapi.entity.UserEntity

fun UserEntity.convertToUserDTO() = User(
    login = this.username,
    authPassword = this.password
)

fun Animal.convertToAnimalEntity(enclosure: EnclosureEntity) = AnimalEntity(
    id = this.id,
    name = this.name,
    species = this.species,
    dateOfBirth = this.dateOfBirth,
    arrivalDate = this.arrivalDate,
    origin = this.origin,
    enclosure = enclosure
)