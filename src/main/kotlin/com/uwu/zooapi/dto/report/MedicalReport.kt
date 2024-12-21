package com.uwu.zooapi.dto.report

data class MedicalReport(
    val animalName: String,
    val checkupDate: String,
    val diagnosis: String,
    val treatment: String,
    val staff: String,
    val checkupCount: String
)
