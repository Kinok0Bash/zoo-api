package com.uwu.zooapi.enum

enum class TicketType {
    CHILD,
    ADULT,
    FAMILY
}

fun TicketType.value(): String {
    return when(this) {
        TicketType.CHILD -> "Детский"
        TicketType.ADULT -> "Взрослый"
        TicketType.FAMILY -> "Семейный"
    }
}