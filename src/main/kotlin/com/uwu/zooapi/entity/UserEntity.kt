package com.uwu.zooapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "Users")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Int = 0,

    @Column(name = "username", nullable = false, unique = true)
    val username: String = "",

    @Column(name = "password", nullable = false)
    val password: String = ""
)
