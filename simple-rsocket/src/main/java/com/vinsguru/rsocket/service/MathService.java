package com.vinsguru.rsocket.service;

import com.vinsguru.rsocket.dto.ChartResponseDto;
import com.vinsguru.rsocket.dto.RequestDto;
import com.vinsguru.rsocket.dto.ResponseDto;
import com.vinsguru.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MathService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Receiving : " + ObjectUtil.toObject(payload, RequestDto.class));
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.fromSupplier(() -> {
            RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
            ResponseDto responseDto = new ResponseDto(requestDto.getInput(), requestDto.getInput() * requestDto.getInput());
            return ObjectUtil.toPayload(responseDto);
        });
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
        return Flux.range(1, 10)
                    .map(i -> i * requestDto.getInput())
                    .map(i -> new ResponseDto(requestDto.getInput(), i))
                    .delayElements(Duration.ofSeconds(1))
                    .doOnNext(System.out::println)
                    .doFinally(s -> System.out.println(s))
                    .map(ObjectUtil::toPayload);
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        return Flux.from(payloads)
                    .map(p -> ObjectUtil.toObject(p, RequestDto.class))
                    .map(RequestDto::getInput)
                    .map(i -> new ChartResponseDto(i, (i * i) + 1))
                    .map(ObjectUtil::toPayload);
    }
}
