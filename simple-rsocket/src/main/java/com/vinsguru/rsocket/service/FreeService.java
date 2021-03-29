package com.vinsguru.rsocket.service;

import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FreeService extends MathService {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.empty();
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        return super.requestStream(payload).take(3);
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        return super.requestChannel(payloads).take(3);
    }
}
