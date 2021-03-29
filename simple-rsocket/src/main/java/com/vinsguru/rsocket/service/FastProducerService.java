package com.vinsguru.rsocket.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;

public class FastProducerService implements RSocket {

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        return Flux.range(1, 1000)
                    .map(i -> i + "")
                    .doOnNext(System.out::println)
                    .doFinally(System.out::println)
                    .map(DefaultPayload::create);
    }
}
