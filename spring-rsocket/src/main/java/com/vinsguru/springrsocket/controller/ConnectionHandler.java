package com.vinsguru.springrsocket.controller;

import com.vinsguru.springrsocket.dto.ClientConnectionRequest;
import com.vinsguru.springrsocket.service.MathClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ConnectionHandler {

    @Autowired
    private MathClientManager clientManager;

/*    @ConnectMapping
    public Mono<Void> handleConnection(ClientConnectionRequest request, RSocketRequester rSocketRequester){
        System.out.println("connection setup : " + request);
        return request.getSecretKey().equals("password") ? Mono.empty() :
                Mono.fromRunnable(() -> rSocketRequester.rsocketClient().dispose());
    }*/

    @ConnectMapping
    public Mono<Void> noEventConnection(RSocketRequester rSocketRequester){
        System.out.println("connection setup" );
        return Mono.empty();
    }


    @ConnectMapping("math.events.connection")
    public Mono<Void> mathEventConnection(RSocketRequester rSocketRequester){
        System.out.println("math event connection setup" );
        return Mono.fromRunnable(() -> this.clientManager.add(rSocketRequester));
    }

}
