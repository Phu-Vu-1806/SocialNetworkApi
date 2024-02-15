package com.springboot.socialnetworkapi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {

    private String message;
    private Object data;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(Object data) {
        this.data = data;
    }
}
