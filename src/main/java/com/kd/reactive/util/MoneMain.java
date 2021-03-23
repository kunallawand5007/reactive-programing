package com.kd.reactive.util;

import com.kd.reactive.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class MoneMain {

    public static void main(String[] args) {


        List<Student> students =new ArrayList<>();

Student student1=new Student("","kunal","lawand","Male",28);
Student student2=new Student("System.currentTimeMillis()","Vishal","lawand","Male",25);

students.add(student1);
students.add(student2);


        Flux<Student> studentsFlux =Flux.fromIterable(students);


   Mono<Student>  stMono=  studentsFlux.flatMap(s->{
            if(s.getFname().equalsIgnoreCase("unal")){
                return  null;
            }else{
                return  Mono.just(s);
            }
        }).next();

   if (stMono==null){
    System.out.println("Empty..");
   }else{
       System.out.println("Not Empty......!!");
   }






       Student monos= studentsFlux.flatMapSequential(s-> {

            if(s.getFname().equalsIgnoreCase("unal")){
                return Mono.just(s);
            }else{
                return  Mono.empty();
            }
        }).next().block();


       if(monos==null){
           System.out.println("Not Found");
       }else{
           System.out.println("Found..");
       }





  //      Using operators .switchIfEmpty/.defaultIfEmpty/Mono.repeatWhenEmpty





    }


}
