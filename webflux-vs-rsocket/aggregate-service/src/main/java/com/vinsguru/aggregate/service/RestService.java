package com.vinsguru.aggregate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class RestService {

    private WebClient webClient;

    @Value("${rest.service.url}")
    private String url;

    @PostConstruct
    private void init(){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Map<Integer, Integer>> requestResponse(int input){
        return Flux.range(1, input)
                .flatMap(i ->  this.webClient
                                    .get()
                                    .uri("{input}", i)
                                    .retrieve()
                                    .bodyToMono(Integer.class)
                                    .map(k -> Tuples.of(i, k))
                )
                .collectMap(Tuple2::getT1, Tuple2::getT2);
    }

}
