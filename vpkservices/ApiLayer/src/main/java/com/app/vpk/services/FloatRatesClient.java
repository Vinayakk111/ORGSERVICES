package com.app.vpk.services;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.vpk.dto.FloatRatesResponse;

import reactor.util.retry.Retry;

@Component
public class FloatRatesClient {

	private final WebClient webClient;

	@Value("${fx.floatrates.url}")
	private String url;

	public FloatRatesClient(WebClient webClient) {

		this.webClient = webClient;
	}

	public FloatRatesResponse getUsdRates() {

		return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(FloatRatesResponse.class).timeout(Duration.ofSeconds(5))
				.retryWhen(Retry.backoff(3, Duration.ofSeconds(1))).block();
	}
}
