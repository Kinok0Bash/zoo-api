package com.uwu.zooapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class ZooApiApplication

fun main(args: Array<String>) {
    runApplication<ZooApiApplication>(*args)
}
