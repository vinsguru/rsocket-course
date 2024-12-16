package com.vinsguru.springrsocket;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import io.rsocket.loadbalance.WeightedLoadbalanceStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties =
        {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
        }
)
public class Lec11ClientSideLoadBalancingTest {

    @Autowired
    private Flux<List<LoadbalanceTarget>> targets;

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    public void clientSide() throws InterruptedException {

        RSocketRequester requester = this.builder.transports(targets, WeightedLoadbalanceStrategy.create());

        for (int i = 0; i < 50; i++) {
            requester.route("math.service.print").data(new ComputationRequestDto(i)).send().subscribe();

            Thread.sleep(200);
        }

    }

}
