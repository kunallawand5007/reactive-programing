package com.kd.reactive.flux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class FluxCreationExample {
    public static void main(String[] args) {

        //creating simple flux

        System.out.println("######################  Case 1");

        Flux<String> strFlux =Flux.just("Kunal","Vishal","Rahul","Akki","Machar","Bhanu").log();
        //strFlux.subscribe(System.out::println);

        System.out.println("######################  Case 2");

        // creating flux with exception type
        Flux<String> strFlux1= Flux.just("Maharashtra","Mumbai","Nagar")
                .concatWith(Flux.error(new RuntimeException("Checking for error"))).log();

        strFlux1.subscribe(System.out::println,(e)-> log.info(e.getMessage()));

        log.info("########### Case 3");
        // adding flux after exception

        Flux<String>  flStr = Flux.just("Alti","Nitor","Kahuna")
                .concatWith(Flux.error(new RuntimeException("exception checking....")))
                .concatWith(Flux.just("adding after exception")).log();

        flStr.subscribe(System.out::println,(e)-> log.info(e.getMessage()));


        // Flux creation with factory method
            log.info("Flux creation with factory method........................");
        List<String> cities = Arrays.asList("Ahmednagar","Pune","Mumbai","Nashik","Nanded");

        Flux<String> cityFlux = Flux.fromIterable(cities).log();
        cityFlux.subscribe(System.out::println);


        Flux<String> streamFlux = Flux.fromStream(cities.stream());

        Flux<String> n = streamFlux.filter(s -> s.startsWith("N"));

        n.subscribe(System.out::println);

        streamFlux.sort((v1,v2)->{
            return   v1.compareTo(v2);
        });



    }




/*    factory method.,
    filter
    concat ,merge  flux


 */

//    Flux<String> flux = Flux.just("A", "B", "C");
//    Flux<String> flux = Flux.fromArray(new String[]{"A", "B", "C"});
//    Flux<String> flux = Flux.fromIterable(Arrays.asList("A", "B", "C"));



}
