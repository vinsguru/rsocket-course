package com.vinsguru.userservice;

import com.vinsguru.userservice.dto.*;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTransactionTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setRequester(){
        this.requester = this.builder
                .transport(TcpClientTransport.create("localhost", 7071));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void transactionTest(int amount, TransactionType type, TransactionStatus status) {
        UserDto dto = this.getRandomUser();
        TransactionRequest request = new TransactionRequest(dto.getId(), amount, type);
        Mono<TransactionResponse> mono = this.requester.route("user.transaction")
                .data(request)
                .retrieveMono(TransactionResponse.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextMatches(r -> r.getStatus().equals(status))
                .verifyComplete();
    }

    private Stream<Arguments> testData(){
        return Stream.of(
                Arguments.of(2000, TransactionType.CREDIT, TransactionStatus.COMPLETED),
                Arguments.of(2000, TransactionType.DEBIT, TransactionStatus.COMPLETED),
                Arguments.of(12000, TransactionType.DEBIT, TransactionStatus.FAILED)
        );
    }


    private UserDto getRandomUser(){
        return this.requester.route("user.get.all")
                .retrieveFlux(UserDto.class)
                .next()
                .block();
    }


}
