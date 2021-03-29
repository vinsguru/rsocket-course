package com.vinsguru.tradingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class RSocketClientConfig {

    @Bean
    public RSocketConnectorConfigurer connectorConfigurer(){
        return c -> c.reconnect(retryStrategy());
    }

    private Retry retryStrategy(){
        return Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(2))
                    .doBeforeRetry(s -> System.out.println("Lost connection. Retry : " + s.totalRetriesInARow()));
    }

}
