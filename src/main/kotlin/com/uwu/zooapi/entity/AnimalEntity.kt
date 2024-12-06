package com.uwu.zooapi.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "Animals")
data class AnimalEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    val id: Int = 0,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "species", nullable = false)
    val species: String,

    @Column(name = "date_of_birth")
    val dateOfBirth: LocalDate?,

    @Column(name = "arrival_date")
    val arrivalDate: LocalDate?,

    @Column(name = "origin")
    val origin: String?,

    @ManyToOne
    @JoinColumn(name = "enclosure_id")
    val enclosure: EnclosureEntity?
)
