package com.uwu.zooapi.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "Medical_checkups")
data class MedicalCheckupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkup_id")
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "animal_id")
    val animal: AnimalEntity?,

    @ManyToOne
    @JoinColumn(name = "staff_id")
    val staff: StaffEntity?,

    @Column(name = "checkup_date", nullable = false)
    val checkupDate: LocalDate,

    @Column(name = "diagnosis")
    val diagnosis: String?,

    @Column(name = "treatment")
    val treatment: String?
)
