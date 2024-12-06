package com.uwu.zooapi.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "Enclosures")
data class EnclosureEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enclosure_id")
    val id: Int = 0,

    @Column(name = "size", nullable = false)
    val size: BigDecimal,

    @Column(name = "temperature", nullable = false)
    val temperature: BigDecimal,

    @Column(name = "description")
    val description: String?
)
