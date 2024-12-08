package com.uwu.zooapi.service

import com.uwu.zooapi.dto.request.AuthenticationRequest
import com.uwu.zooapi.dto.response.AuthenticationResponse
import com.uwu.zooapi.entity.UserEntity
import com.uwu.zooapi.repository.UserRepository
import com.uwu.zooapi.util.convertToUserDTO
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationService::class.java)

    @Transactional
    fun authorization(request: AuthenticationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidCredentials(request)) {
            logger.error("Поля логин и/или пароль пустые")
            throw Exception("Поля логин и/или пароль пустые")
        }
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.login, request.password))
        val user = userRepository.findByUsername(request.login)
        logger.debug("Пользователь ${user.username} авторизован")
        logger.info("Авторизация прошла успешно!")

        val userDetails = user.convertToUserDTO()

        val tokens = jwtService.generateTokens(userDetails)

        setRefreshToken(response, tokens[1])

        return AuthenticationResponse(tokens[0], user.username)
    }

    @Transactional
    fun registration(request: AuthenticationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidCredentials(request)) {
            logger.error("Заполнены не все данные")
            throw Exception("Заполнены не все данные")
        }

        val usernames = userRepository.findAllUsernames()

        usernames.forEach { username ->
            if (request.login == username) {
                logger.error("Ошибка регистрации: Пользователь с username ${request.login} уже существует")
                throw Exception("Пользователь с таким username уже существует")
            }
        }

        val user = UserEntity (
            username = request.login,
            password = passwordEncoder.encode(request.password)
        )

        val userDetails = user.convertToUserDTO()

        val tokens = jwtService.generateTokens(userDetails)

        userRepository.save(user)
        setRefreshToken(response, tokens[1])

        logger.debug("Пользователь с username ${user.username} был создан")
        logger.info("Регистрация прошла успешно")

        return AuthenticationResponse(tokens[0], user.username)
    }

    fun logout(token: String, response: HttpServletResponse): Map<String, String> {
        val cookie = Cookie("refreshToken", null)
        cookie.maxAge = 0
        cookie.path = "/"
        response.addCookie(cookie)

        return mapOf("message" to "Logout successful")
    }

    @Transactional
    fun refresh(userToken: String, response: HttpServletResponse): AuthenticationResponse {
        if (userToken.isEmpty()) {
            logger.error("Токен пуст")
            throw Exception("Токен пуст")
        }

        val user = userRepository.findByUsername(jwtService.extractUsername(userToken))

        val userDetails = user.convertToUserDTO()
        val tokens = jwtService.generateTokens(userDetails)

        userRepository.save(user)
        setRefreshToken(response, tokens[1])

        logger.debug("Токен пользователя ${user.username} обновлен")

        return AuthenticationResponse(tokens[0], user.username)
    }

    fun whoAmI(token: String): String {
        val user = userRepository.findByUsername(jwtService.extractUsername(token.substring(7)))
        logger.info("WhoAmI для пользователя ${user.username} был получен")
        return user.username
    }

    private fun setRefreshToken(response: HttpServletResponse, token: String) {
        val cookie = ResponseCookie.from("refreshToken", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofDays(30))
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    private fun isValidCredentials(request: AuthenticationRequest) =
        request.login.isNotEmpty() && request.password.isNotEmpty()

}