package com.vinsguru.aggregate.service;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RSocketService {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @Value("${rsocket.service.host}")
    private String host;

    @Value("${rsocket.service.port}")
    private int port;

    @PostConstruct
    private void init(){
        this.requester = this.builder.transport(TcpClientTransport.create(host, port));
    }

    public Mono<Map<Integer, Integer>> requestResponse(int input){
        return Flux.range(1, input)
                .flatMap(i ->  this.requester.route("rr.square")
                        .data(i)
                        .retrieveMono(Integer.class)
                        .map(k -> Tuples.of(i, k))
                )
                .collectMap(Tuple2::getT1, Tuple2::getT2);
    }

    public Mono<Map<Integer, Integer>> requestChannel(int input){
        AtomicInteger atomicInteger = new AtomicInteger(1);
        return this.requester.route("rc.square")
                .data(Flux.range(1, input))
                .retrieveFlux(Integer.class)
                .collectMap(i -> atomicInteger.getAndIncrement(), i -> i);
    }

}
