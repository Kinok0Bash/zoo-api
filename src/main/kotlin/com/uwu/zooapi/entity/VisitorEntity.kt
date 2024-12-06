package com.uwu.zooapi.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "Visitors")
data class VisitorEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitor_id")
    val id: Int = 0,

    @Column(name = "name", nullable = false)
    val name: String = "",

    @Column(name = "visit_date", nullable = false)
    val visitDate: LocalDate = LocalDate.now(),

    @Column(name = "feedback")
    val feedback: String = ""
)
