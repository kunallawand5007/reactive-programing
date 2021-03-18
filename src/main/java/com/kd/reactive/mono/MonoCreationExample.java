package com.kd.reactive.mono;

import reactor.core.publisher.Mono;

public class MonoCreationExample {


    public static void main(String[] args) {

        // Mono represent only one element

        Mono<String> mono =   Mono.just("Kunal")
                .log();

        mono.subscribe(System.out::println);

        //create mono with error




    }

}
