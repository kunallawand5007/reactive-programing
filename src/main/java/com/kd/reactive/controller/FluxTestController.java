package com.kd.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxTestController {


    @GetMapping(value = "/simple/flux/test",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Flux<Integer> createFlux(){
        return  Flux.just(1,2,3,4,5).log();
    }

    @GetMapping(value = "/simple/mono/test")
    public Mono<Integer> createMono(){
        return  Mono.just(1).log();
    }


}

