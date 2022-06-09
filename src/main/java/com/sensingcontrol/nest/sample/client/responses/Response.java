package com.sensingcontrol.nest.sample.client.responses;

import java.util.ArrayList;
import java.util.List;

public class Response {
    public List<String> errors;

    public Response()
    {
        errors = new ArrayList<>();
    }
}
