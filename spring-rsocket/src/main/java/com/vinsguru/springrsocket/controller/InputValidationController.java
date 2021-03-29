package com.vinsguru.springrsocket.controller;

import com.vinsguru.springrsocket.dto.Response;
import com.vinsguru.springrsocket.dto.error.ErrorEvent;
import com.vinsguru.springrsocket.dto.error.StatusCode;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.validation")
public class InputValidationController {

    @MessageMapping("double.{input}")
    public Mono<Integer> doubleIt(@DestinationVariable int input){
        return Mono.just(input)
                    .filter(i -> i < 31)
                    .map(i -> i * 2)
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("cannot > 30")));
    }

    @MessageMapping("double.response.{input}")
    public Mono<Response<Integer>> doubleResponse(@DestinationVariable int input){
        return Mono.just(input)
                .filter(i -> i < 31)
                .map(i -> i * 2)
                .map(Response::with)
                .defaultIfEmpty(Response.with(new ErrorEvent(StatusCode.EC001)));
    }

    @MessageExceptionHandler
    public Mono<Integer> handleException(Exception exception){
        return Mono.just(-1);
    }


/*    @MessageMapping("double.{input}")
    public Mono<Integer> doubleIt(@DestinationVariable int input){
        if(input < 31)
            return Mono.just(input * 2);
        else
            return Mono.error(new IllegalArgumentException("can not be > 30"));
    }*/


}
