package com.vinsguru.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest-service")
public class SquareController {

    @GetMapping("rest/square/{input}")
    public Integer findSquare(@PathVariable int input){
        return input * input;
    }

}
