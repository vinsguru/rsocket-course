package com.vinsguru.rsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class SquareController {

    @MessageMapping("rr.square")
    public Mono<Integer> findSquare(Mono<Integer> integerMono){
        return integerMono.map(i -> i * i);
    }

    @MessageMapping("rc.square")
    public Flux<Integer> findSquare(Flux<Integer> integerFlux){
        return integerFlux.map(i -> i * i);
    }

}
