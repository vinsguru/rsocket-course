package com.vinsguru.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
        System.out.println("SocketAcceptorImpl-accept method");

        if(isValidClient(connectionSetupPayload.getDataUtf8()))
            return Mono.just(new MathService());
        else
            return Mono.just(new FreeService());
        //Mono.fromCallable(MathService::new);
        //return Mono.fromCallable(FastProducerService::new);
    }

    private boolean isValidClient(String credentials){
        return "user:password".equals(credentials);
    }

}
