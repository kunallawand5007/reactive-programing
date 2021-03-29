package com.kd.reactive.handler.func;

import com.kd.reactive.model.Response;
import com.kd.reactive.model.Student;
import com.kd.reactive.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
public class HandlerReqResFunction {

    @Autowired
    private StudentRepository studentRepository;

    Mono<ServerResponse>  notFound= ServerResponse.notFound().build();


    public Mono<ServerResponse>  simpleFlux(ServerRequest serverRequest){

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.just(1,2,3,4,5).log(),Integer.class);

    }

    //Mono<String> string = request.body(BodyExtractors.toMono(String.class));
    //Flux<Person> people = request.body(BodyExtractors.toFlux(Person.class));

    // Register/Create student
    public  Mono<ServerResponse>  registerStudentInMongo(ServerRequest serverRequest){

        Mono<Student> studentMono = serverRequest.bodyToMono(Student.class);


       return studentMono.flatMap(item->
             ServerResponse.ok()
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(studentRepository.save(item),Student.class)).log();

    }

    /*
     * <p> This Handlerfunction for find StudentBY Id using path variable</p>
     *
     */
    public Mono<ServerResponse> findStudentById(ServerRequest serverRequest){

  Mono<ServerResponse>  notFound= ServerResponse.notFound().build();

        String studentId = serverRequest.pathVariable("studentId");

        Mono<Student> byId = studentRepository.findById(studentId);

        byId.subscribe(System.out::println);

//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(byId,Student.class)
//                .switchIfEmpty(ServerResponse.notFound().build()).log();

        return byId.flatMap(student ->
                ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(student))
                .switchIfEmpty(notFound));


    }


    /*
     * <p> This Handlerfunction for find StudentBY Id using request param</p>
     *
     */
    public Mono<ServerResponse> findStudentByIdRequestParam(ServerRequest serverRequest){

        //final Optional<String> name = serverRequest.queryParam("name");
        //final Optional<String> age = serverRequest.queryParam("age");

        MultiValueMap<String, String> stringStringMultiValueMap = serverRequest.queryParams();

        log.info("Param1:{}",stringStringMultiValueMap.get("studentId"));
        log.info("Param2:{}",stringStringMultiValueMap.get("fname"));

        Optional<String> studentId = serverRequest.queryParam("studentId");
        //Mono<Student> byId = studentRepository.findById(studentId.get());
        Mono<Student> byId = studentRepository.findById(stringStringMultiValueMap.get("studentId").get(0));
        byId.subscribe(System.out::println);

        return byId.flatMap(student ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(byId,Student.class)
                        .switchIfEmpty(notFound));

    }

    /*
     * <p> This Handlerfunction for find all Students</p>
     *
     */
        public Mono<ServerResponse> findAllStudents(ServerRequest serverRequest){

        return  ServerResponse.ok()
                .body(studentRepository.findAll(),Student.class)
                .log();
        }

        /*
         *<p> This method for showing how to handle errors at functional level </p>
         *
         *
         */

        public Mono<ServerResponse> sayHello(ServerRequest serverRequest){




            // Way-1 Simple way to handle

//           return  createHello(serverRequest)
//                   .onErrorReturn(errorResponse.toString())
//                   .flatMap(response -> ServerResponse
//                            .ok()
//                           .contentType(MediaType.APPLICATION_JSON)
//                           .bodyValue(response) );

            // Way-2 calling fallback method

//            return createHello(serverRequest)
//              .flatMap(response ->
//                      ServerResponse
//                              .ok()
//                              .contentType(MediaType.APPLICATION_JSON)
//                              .bodyValue(response).log()
//                      ).onErrorResume(e -> fallbackHello()
//                    .flatMap(errorResponse ->
//                             ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .bodyValue(errorResponse).log()
//                    ));

            // Way-3 calling with dynamic error message

//            return  createHello(serverRequest)
//                    .flatMap(response ->
//                             ServerResponse.ok()
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(response).log())
//                    .onErrorResume(e -> (Mono.just(e.getMessage())
//                            .flatMap(er->
//                              ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .bodyValue(er).log()
//                                    )));


            // Way-4 throwing exception

           return  createHello(serverRequest)
                   .flatMap(response ->
                           ServerResponse.ok()
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .bodyValue(response).log()
                   ).onErrorResume(error -> Mono.error(new RuntimeException( error.getLocalizedMessage())));


        }


        // create Method for returning response

      public Mono<String> createHello(ServerRequest serverRequest){

          try{
              Optional<String> username = serverRequest.queryParam("username");

            return  Mono.just("Hello:"+username.get());

          }catch (Exception exception){
              return  Mono.error(exception);
          }
      }

      public Mono<Response> fallbackHello(){
          Response errorResponse = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR.name());
          return  Mono.just(errorResponse);
      }




}

//https://github.com/eugenp/tutorials/tree/master/spring-5-reactive