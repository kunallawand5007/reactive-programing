package com.kd.reactive.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class FluxCreationTest {

    @Test
    public void createFlux(){

    Flux<String> flux  = Flux.just("Kunal","Lawand");

        StepVerifier.create(flux)
                .expectNext("Kunal")
                .expectNext("Lawand")
                .verifyComplete(); // no event flow if you not called this method...

     //if we change order test case will failed

    }


    @Test
    public void createFluxException(){

        Flux<String> flux  = Flux.just("Kunal","Lawand")
                .concatWith(Flux.error(new RuntimeException("checking exception..with test"))).log();

        StepVerifier.create(flux)
                .expectNext("Kunal")
                .expectNext("Lawand")
                 .expectError(RuntimeException.class)
                .verify();
    }


    @Test
    public void createFluxExceptionMessage(){

        Flux<String> flux  = Flux.just("Kunal","Lawand")
                .concatWith(Flux.error(new RuntimeException("checking exception..with test"))).log();

        StepVerifier.create(flux)
                .expectNext("Kunal")
                .expectNext("Lawand")
                .expectErrorMessage("checking exception..with test")
                .verify();
    }

    @Test
    public void createFluxWithError(){

        Flux<String> flux  = Flux.just("Kunal","Lawand")
                .concatWith(Flux.error(new RuntimeException("checking exception..with test"))).log();

        StepVerifier.create(flux)
                .expectNextCount(2)
                .expectErrorMessage("checking exception..with test")
                .verify();
    }

}
