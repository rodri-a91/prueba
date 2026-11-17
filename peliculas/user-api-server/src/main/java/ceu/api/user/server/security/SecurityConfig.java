package ceu.api.user.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final ApiKeyFilter apiKeyFilter;

	@Bean
	SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		// Permitimos hacer peticiones POST/PUT/DELETE
		http.csrf(c -> c.disable());
		// Desactivamos formulario de login añadido por spring security
		http.formLogin(f -> f.disable());
		// Añadimos nuestro filtro. Hay que hacerlo en algún lugar. Lo hacemos delante
		// de otro
		// ya existente de Spring Security
		http.addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
