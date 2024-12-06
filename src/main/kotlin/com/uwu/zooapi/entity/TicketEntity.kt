package com.uwu.zooapi.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "Tickets")
data class TicketEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    val visitor: VisitorEntity = VisitorEntity(),

    @Column(name = "ticket_type", nullable = false)
    val ticketType: String = "",

    @Column(name = "price", nullable = false)
    val price: BigDecimal = BigDecimal.valueOf(0),

    @Column(name = "purchase_date", nullable = false)
    val purchaseDate: LocalDate = LocalDate.now()
)