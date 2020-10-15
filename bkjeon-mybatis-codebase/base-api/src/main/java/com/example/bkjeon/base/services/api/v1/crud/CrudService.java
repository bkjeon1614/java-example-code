package com.example.bkjeon.base.services.api.v1.crud;

import org.springframework.stereotype.Service;

@Service
public class CrudService {

    public String getCallMethod() {
        return "Request Get";
    }
    public String setCallMethod() {
        return "Request Post";
    }
    public String putCallMethod() {
        return "Request Put";
    }
    public String patchCallMethod() {
        return "Request Patch";
    }
    public String delCallMethod() {
        return "Request Delete";
    }

}
