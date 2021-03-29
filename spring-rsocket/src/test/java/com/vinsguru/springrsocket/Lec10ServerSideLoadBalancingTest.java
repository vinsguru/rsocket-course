package com.vinsguru.springrsocket;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties =
        {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
        }
)
public class Lec10ServerSideLoadBalancingTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    public void connectionTest() throws InterruptedException {

        RSocketRequester requester1 = this.builder
                .transport(TcpClientTransport.create("localhost", 6566));

        RSocketRequester requester2 = this.builder
                .transport(TcpClientTransport.create("localhost", 6566));

        for (int i = 0; i < 50; i++) {
            requester1.route("math.service.print").data(new ComputationRequestDto(i)).send().subscribe();
            requester2.route("math.service.print").data(new ComputationRequestDto(i)).send().subscribe();

            Thread.sleep(2000);
        }

    }

}
