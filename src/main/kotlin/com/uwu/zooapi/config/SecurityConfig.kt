package com.uwu.zooapi.config

import com.uwu.zooapi.service.JwtService
import com.uwu.zooapi.util.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .addFilterBefore(
                JwtAuthenticationFilter(userDetailsService, jwtService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .authorizeHttpRequests { authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                        "/api/auth/who-am-i",
                        "/api/tickets/visit",
                        "/api/animals/*",
                        "/api/reports/animals",
                        "/api/reports/medical",
                        "/api/reports/tickets",
                    )
                    .authenticated()
                    .anyRequest()
                    .permitAll()
            }
            .exceptionHandling { exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        return http.build()
    }

}