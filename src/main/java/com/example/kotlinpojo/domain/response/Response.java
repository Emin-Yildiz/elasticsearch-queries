package com.example.kotlinpojo.domain.response;

public record Response <T> (Boolean success, String message, T data){
    public Response(String message, T data){this(Boolean.TRUE,message,data);}}
