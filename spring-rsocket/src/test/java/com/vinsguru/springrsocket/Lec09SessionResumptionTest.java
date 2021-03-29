package com.vinsguru.springrsocket;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;
import io.rsocket.core.Resume;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootTest
@TestPropertySource(properties =
        {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
        }
)
public class Lec09SessionResumptionTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    public void connectionTest() {

        RSocketRequester requester = this.builder
                .rsocketConnector(c -> c
                        .resume(resumeStrategy())
                        .reconnect(retryStrategy()))
                .transport(TcpClientTransport.create("localhost", 6566));

        Flux<ComputationResponseDto> flux = requester.route("math.service.table")
                .data(new ComputationRequestDto(5))
                .retrieveFlux(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(1000)
                .verifyComplete();


    }

    private Resume resumeStrategy(){
        return new Resume()
                    .retry(Retry.fixedDelay(2000, Duration.ofSeconds(2))
                            .doBeforeRetry(s -> System.out.println("resume - retry :" + s.totalRetriesInARow())));
    }

    private Retry retryStrategy(){
        return Retry.fixedDelay(100, Duration.ofSeconds(1))
                    .doBeforeRetry(s -> System.out.println("Retrying connection : " + s.totalRetriesInARow()));
    }

}
