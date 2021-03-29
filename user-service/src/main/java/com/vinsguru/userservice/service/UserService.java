package com.vinsguru.userservice.service;

import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Flux<UserDto> getAllUsers(){
        return this.repository.findAll()
                        .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(final String userId){
        return this.repository.findById(userId)
                        .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> mono){
        return mono
                    .map(EntityDtoUtil::toEntity)
                    .flatMap(this.repository::save)
                    .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(String id, Mono<UserDto> mono){
        return this.repository.findById(id)
                            .flatMap(u -> mono.map(EntityDtoUtil::toEntity)
                                           .doOnNext(e -> e.setId(id)))
                            .flatMap(this.repository::save)
                            .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(String id){
        return this.repository.deleteById(id);
    }


}
