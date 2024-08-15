package com.blisspace.app.infrastructure.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebConfiguration(
    private val environment: Environment
) {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    if (environment.activeProfiles.isEmpty() || environment.activeProfiles.contains("local")) {
      http
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .csrf { it.disable() }
    } else {
      http
        .authorizeHttpRequests { it.anyRequest().denyAll() }
        .csrf { it.disable() }
    }
    return http.build()
  }
}