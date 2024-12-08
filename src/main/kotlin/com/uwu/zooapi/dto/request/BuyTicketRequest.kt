package com.uwu.zooapi.dto.request

import com.uwu.zooapi.enum.TicketType

data class BuyTicketRequest(
    val name: String,
    val ticketType: TicketType,
    val price: Double
)
