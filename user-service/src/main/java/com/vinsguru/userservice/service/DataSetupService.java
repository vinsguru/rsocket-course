package com.vinsguru.userservice.service;

import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("sam", 10000);
        User user2 = new User("mike", 10000);
        User user3 = new User("jake", 10000);

        Flux.just(user1, user2, user3)
                .flatMap(this.repository::save)
                .doOnNext(System.out::println)
                .subscribe();
    }
}
