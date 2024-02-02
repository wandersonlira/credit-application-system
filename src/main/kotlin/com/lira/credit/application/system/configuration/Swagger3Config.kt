package com.lira.credit.application.system.configuration

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Swagger3Config {

    @Bean
    fun publicAPI(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("springcredtapplicationsystem-public")
            .pathsToMatch("/api/customers/**", "/api/credits/**")
            .build()
    }
}