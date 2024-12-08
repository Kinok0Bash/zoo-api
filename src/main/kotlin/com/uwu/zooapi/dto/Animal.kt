package com.uwu.zooapi.dto

import java.time.LocalDate

data class Animal(
    val id: Int = 0,
    val name: String = "",
    val species: String = "",
    val dateOfBirth: LocalDate = LocalDate.now(),
    val arrivalDate: LocalDate = LocalDate.now(),
    val origin: String = "",
    val enclosure: Int = 0
)
