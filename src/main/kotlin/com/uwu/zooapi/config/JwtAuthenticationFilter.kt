package com.uwu.zooapi.config

import com.uwu.zooapi.service.JwtService
import com.uwu.zooapi.util.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader("Authorization")

            if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                logger.warn("Отсутствует токен авторизации")
                return
            }

            val jwt = authHeader.substring(7)
            val userEmail = jwtService.extractUsername(jwt)

            if (userEmail.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = this.userDetailsService.loadUserByUsername(userEmail)

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(userEmail, null, userDetails.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                } else throw JwtException()
            }

            filterChain.doFilter(request, response)
        } catch (ex: JwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Срок действия токена истек")
            logger.warn("Срок действия токена истек")
            return
        }
    }

}