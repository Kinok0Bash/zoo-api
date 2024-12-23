package com.uwu.zooapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Система управления зоопарком",
        description = "Система управления зоопарком для курсовой работы по Базам Данных",
        contact = Contact(
            name = "Гринько Максим ВПР33"
        ),
        version = "1.0.0"
    )
)
class SwaggerConfig