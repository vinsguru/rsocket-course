package com.vinsguru.userservice.controller;

import com.vinsguru.userservice.dto.TransactionRequest;
import com.vinsguru.userservice.dto.TransactionResponse;
import com.vinsguru.userservice.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("user")
public class UserTransactionController {

    @Autowired
    private UserTransactionService transactionService;

    @MessageMapping("transaction")
    public Mono<TransactionResponse> doTransaction(Mono<TransactionRequest> transactionRequestMono){
        return transactionRequestMono.flatMap(this.transactionService::doTransaction);
    }

}
