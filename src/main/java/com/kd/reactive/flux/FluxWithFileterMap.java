package com.kd.reactive.flux;

import com.kd.reactive.model.Student;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxWithFileterMap {


    public static void main(String[] args) {

        Student student1 =new Student("Kunal","Lawand",30);
        Student student2= new Student("Vishal","Lawand",26);
        Student student3 =new Student("Rahul","Gangarde",28);
        Student student4 =new Student("prashnt","mache",29);


        Flux<Student> students = Flux.just(student1, student2, student3, student4);
        Flux<Integer> rahul = students.filter(s -> s.getFname().equals("Rahul")).map(s1-> s1.getAge());
        int age=rahul.blockFirst();
        log.info("Age:{}",age);

         //sorting flux list

       Flux<Student> sorted = students.sort((s1,s2)->{
           return s1.getAge().compareTo(s2.getAge());
        });

        sorted.map(d->d.getAge()).subscribe(System.out::println);



    }





}

