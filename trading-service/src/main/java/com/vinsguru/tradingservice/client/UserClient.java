package com.vinsguru.tradingservice.client;

import com.vinsguru.tradingservice.dto.TransactionRequest;
import com.vinsguru.tradingservice.dto.TransactionResponse;
import com.vinsguru.tradingservice.dto.UserDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final RSocketRequester requester;

    public UserClient(RSocketRequester.Builder builder,
                      RSocketConnectorConfigurer connectorConfigurer,
                      @Value("${user.service.host}") String host,
                      @Value("${user.service.port}") int port){
        this.requester = builder.rsocketConnector(connectorConfigurer)
                                .transport(TcpClientTransport.create(host, port));
    }

    public Mono<TransactionResponse> doTransaction(TransactionRequest transactionRequest){
        return this.requester.route("user.transaction")
                            .data(transactionRequest)
                            .retrieveMono(TransactionResponse.class)
                            .doOnNext(System.out::println);
    }

    public Flux<UserDto> allUsers(){
        return this.requester.route("user.get.all")
                        .retrieveFlux(UserDto.class);
    }

}
