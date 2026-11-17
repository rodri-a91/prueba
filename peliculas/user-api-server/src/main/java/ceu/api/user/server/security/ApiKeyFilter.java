package ceu.api.user.server.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
	@Value("${userapi.apikey}")
	private String apikey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String apiKeyRecibida = request.getHeader("API-KEY");

		if (apiKeyRecibida != null && apiKeyRecibida.equals(apikey)) {
			filterChain.doFilter(request, response);
		}

		else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Apikey recibida incorrecta o no recibida");
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String url = request.getRequestURI();
		if (url.contains("swagger") || url.contains("api-docs")) {
			return true;
		}

		return false;
	}

}
