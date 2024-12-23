package com.uwu.zooapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "animaldiets")
data class AnimalDietEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_id")
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "animal_id")
    val animal: AnimalEntity = AnimalEntity(),

    @Column(name = "diet_type")
    val dietType: String = ""
)
