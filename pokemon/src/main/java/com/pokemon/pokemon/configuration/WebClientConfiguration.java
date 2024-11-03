package com.pokemon.pokemon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://pokeapi.co/api/v2")
                .exchangeStrategies(ExchangeStrategies.builder()
                       .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(256 * 1024 * 1024))
                        .build())
                .build();
    }
}