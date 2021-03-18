package com.kd.reactive.flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoCreationTest {

    @Test
    public void testCreateMono(){

     Mono<String> mono =   Mono.just("Kunal");

        StepVerifier.create(mono).expectNext("Kunal").verifyComplete();
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
    }

    @Test
    public void testCreateMonoWitherror(){

StepVerifier.create(Mono.error(new RuntimeException(
        "hell"
)).log()).expectError(RuntimeException.class).verify();

    }

}
