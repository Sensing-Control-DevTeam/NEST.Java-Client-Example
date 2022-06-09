package com.sensingcontrol.nest.sample.client.responses;

public class DataResponse<T> extends Response {
    private  T data;

    public T getData() {
        return data;
    }
}