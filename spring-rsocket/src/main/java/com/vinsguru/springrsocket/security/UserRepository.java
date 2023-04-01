package com.vinsguru.springrsocket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.Map;

@Repository // for demo purposes
public class UserRepository {

    @Autowired
    private PasswordEncoder encoder;

    private Map<String, UserDetails> db;

    @PostConstruct
    private void init(){
        this.db = Map.of(
                "user", User.withUsername("user").password(encoder.encode("password")).roles("USER").build(),
                "admin", User.withUsername("admin").password(encoder.encode("password")).roles("ADMIN").build(),
                "client", User.withUsername("client").password(encoder.encode("password")).roles("TRUSTED_CLIENT").build()
        );
    }

    public UserDetails findByUsername(String username){
        return this.db.get(username);
    }

}
