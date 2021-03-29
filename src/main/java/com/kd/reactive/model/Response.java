package com.kd.reactive.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {

    private  int statusCode;
    private  String message;
    private  String status;

     public  Response(){

     }


     public Response(int statusCode,String message,String status){
         this.statusCode =statusCode;
         this.message =message;
         this.status = status;

    }


}
