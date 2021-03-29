package com.vinsguru.aggregate.controller;

import com.vinsguru.aggregate.service.RSocketService;
import com.vinsguru.aggregate.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class AggregateController {

    @Autowired
    private RSocketService rSocketService;

    @Autowired
    private RestService restService;

    @GetMapping("rest/square/{input}")
    public Mono<Map<Integer, Integer>> restSquare(@PathVariable int input){
        return this.restService.requestResponse(input);
    }

    @GetMapping("rsocket/rr/square/{input}")
    public Mono<Map<Integer, Integer>> rsocketSquareRR(@PathVariable int input){
        return this.rSocketService.requestResponse(input);
    }

    @GetMapping("rsocket/rc/square/{input}")
    public Mono<Map<Integer, Integer>> rsocketSquareRC(@PathVariable int input){
        return this.rSocketService.requestChannel(input);
    }

}
