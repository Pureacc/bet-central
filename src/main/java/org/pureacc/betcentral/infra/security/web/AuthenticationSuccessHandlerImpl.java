package org.pureacc.betcentral.infra.security.web;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
	private static final DateTimeFormatter COOKIE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
			"EEE, dd MMM yyyy HH:mm:ss 'GMT'")
			.withLocale(Locale.ENGLISH);

	private final CryptoService cryptoService;

	AuthenticationSuccessHandlerImpl(CryptoService cryptoService) {
		this.cryptoService = cryptoService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> headerValues = new ArrayList<>();

		String cookieValue = userDetails.getId() + ":";
		cookieValue += Instant.now()
				.plus(Duration.ofHours(4))
				.getEpochSecond();

		String encryptedCookieValue = cryptoService.encrypt(cookieValue);
		headerValues.add(AuthCookieFilter.COOKIE_NAME + "=" + encryptedCookieValue);

		long maxAgeInSeconds = Duration.ofHours(4)
				.getSeconds();
		if (maxAgeInSeconds > -1) {
			headerValues.add("Max-Age=" + maxAgeInSeconds);

			if (maxAgeInSeconds == 0) {
				headerValues.add("Expires=" + COOKIE_DATE_FORMATTER.format(
						ZonedDateTime.ofInstant(Instant.ofEpochMilli(10000), ZoneOffset.UTC)));
			} else {
				headerValues.add("Expires=" + COOKIE_DATE_FORMATTER.format(ZonedDateTime.now(ZoneOffset.UTC)
						.plusSeconds(maxAgeInSeconds)));
			}
		}

		headerValues.add("SameSite=Strict");
		headerValues.add("Path=/");
		headerValues.add("HttpOnly");
		//		if (this.appProperties.isSecureCookie()) {
		//			headerValues.add("Secure");
		//		}

		response.addHeader("Set-Cookie", String.join("; ", headerValues));
		response.getWriter()
				.print(userDetails.getId());
	}
}
