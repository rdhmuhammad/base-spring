package com.github.basespring.application.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String messages;

    private Boolean success;

    private T data;

    public Response(String defaultSuccess, boolean status) {
        this.messages = defaultSuccess;
        this.success = status;
        this.data = null;
    }
}
