package com.vinsguru.springrsocket.client.config;

import com.vinsguru.springrsocket.client.serviceregistry.RSocketServerInstance;
import com.vinsguru.springrsocket.client.serviceregistry.ServiceRegistryClient;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Configuration
public class LoadBalanceTargetConfig {

    @Autowired
    private ServiceRegistryClient registryClient;

    @Bean
    public Flux<List<LoadbalanceTarget>> targetFlux(){
        return Flux.from(targets());
    }

    private Mono<List<LoadbalanceTarget>> targets(){
        return Mono.fromSupplier(() -> this.registryClient.getInstances()
                .stream()
                .map(server -> LoadbalanceTarget.from(key(server), transport(server)))
                .collect(Collectors.toList()));
    }

    private String key(RSocketServerInstance instance){
        return instance.getHost() + instance.getPort();
    }

    private ClientTransport transport(RSocketServerInstance instance){
        return TcpClientTransport.create(instance.getHost(), instance.getPort());
    }

}
