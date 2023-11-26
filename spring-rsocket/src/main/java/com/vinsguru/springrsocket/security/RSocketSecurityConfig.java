package com.vinsguru.springrsocket.security;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;

@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class RSocketSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RSocketStrategiesCustomizer strategiesCustomizer(){
        return c -> c.encoder(new SimpleAuthenticationEncoder());
    }

    @Bean
    public RSocketMessageHandler messageHandler(RSocketStrategies socketStrategies){
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(socketStrategies);
        handler.getArgumentResolverConfigurer().addCustomResolver(new AuthenticationPrincipalArgumentResolver());
        return handler;
    }

    @Bean
    public PayloadSocketAcceptorInterceptor interceptor(RSocketSecurity security){
        return security
                    .simpleAuthentication(Customizer.withDefaults())
                    .authorizePayload(
                            authorize -> authorize
                                    .setup().permitAll()
                                    .anyRequest().permitAll()
                                    .anyExchange().permitAll()
                            // .setup().hasRole("TRUSTED_CLIENT")
                            //  .route("*.*.*.table").hasRole("ADMIN")
                            //  .route("math.service.secured.square").hasRole("USER")
                    ).build();
    }

}
