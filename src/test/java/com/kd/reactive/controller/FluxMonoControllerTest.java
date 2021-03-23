package com.kd.reactive.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest  // This annotation will only work with class annotate with @restController/@controller not  for @compoent/service/repositry
public class FluxMonoControllerTest {

    @Autowired
    private WebTestClient testClient;

    //approach-1

    @Test
    public void testSimpleFlux(){

        Flux<Integer> responseBody = testClient.get().uri("/simple/flux/test")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Integer.class)
                .getResponseBody();


        StepVerifier.create(responseBody)
                .expectNextCount(5)
                .verifyComplete();
    }

    //approach 2
    @Test
    public void testSimpleFluxApproach2(){

        testClient.get().uri("/simple/flux/test")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Integer.class)
                .hasSize(5);
    }

    //Approach-3
    @Test
    public void testSimpleFluxApproach3(){

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        EntityExchangeResult<List<Integer>> listEntityExchangeResult =
                testClient.get().uri("/simple/flux/test")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        Assertions.assertEquals(integers,listEntityExchangeResult.getResponseBody());

    }
    //Approach-4
    @Test
    public void testSimpleFluxApproach4(){

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);


                testClient.get().uri("/simple/flux/test")
                        .accept(MediaType.APPLICATION_STREAM_JSON)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(Integer.class)
                        .consumeWith((response) -> {
                            Assertions.assertEquals(integers,response.getResponseBody());
                        });



    }

}


