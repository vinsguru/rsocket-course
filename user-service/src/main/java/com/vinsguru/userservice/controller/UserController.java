package com.vinsguru.userservice.controller;

import com.vinsguru.userservice.dto.OperationType;
import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// crud demo

@Controller
@MessageMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    // RS
    @MessageMapping("get.all")
    public Flux<UserDto> allUsers(){
        return this.service.getAllUsers();
    }

    // RR
    @MessageMapping("get.{id}")
    public Mono<UserDto> getUserById(@DestinationVariable String id){
        return this.service.getUserById(id);
    }

    // RR
    @MessageMapping("create")
    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono){
        return this.service.createUser(userDtoMono);
    }

    // RR
    @MessageMapping("update.{id}")
    public Mono<UserDto> updateUser(@DestinationVariable String id, Mono<UserDto> userDtoMono){
        return this.service.updateUser(id, userDtoMono);
    }

    // FF
    @MessageMapping("delete.{id}")
    public Mono<Void> deleteUser(@DestinationVariable String id){
        return this.service.deleteUser(id);
    }

    @MessageMapping("operation.type")
    public Mono<Void> metadataOperationType(@Header("operation-type") OperationType operationType,
                                            Mono<UserDto> userDtoMono){
        System.out.println(operationType);
        userDtoMono.doOnNext(System.out::println).subscribe();
        return Mono.empty();
    }

}
