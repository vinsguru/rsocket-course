package com.vinsguru.springrsocket.service;

import com.vinsguru.springrsocket.dto.ChartResponseDto;
import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MathService {

    // ff
    public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono){
        return requestDtoMono
                    .doOnNext(System.out::println)
                    .then();
    }

    // rr
    public Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> requestDtoMono){
        return requestDtoMono
                    .map(ComputationRequestDto::getInput)
                    .map(i -> new ComputationResponseDto(i, i * i));
    }

    // rs
    public Flux<ComputationResponseDto> tableStream(ComputationRequestDto dto){
        return Flux.range(1, 10)
                    .delayElements(Duration.ofSeconds(1))
                    .map(i -> new ComputationResponseDto(dto.getInput(), dto.getInput() * i));
    }

    // rc - x ^2 + 1
    public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux){
        return requestDtoFlux
                    .map(ComputationRequestDto::getInput)
                    .map(i -> new ChartResponseDto(i, (i * i) + 1));
    }

}
