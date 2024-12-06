package com.uwu.zooapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "Staff")
data class StaffEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    val id: Int = 0,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "position")
    val position: String?,

    @ManyToOne
    @JoinColumn(name = "assigned_enclosure_id")
    val assignedEnclosure: EnclosureEntity?
)
