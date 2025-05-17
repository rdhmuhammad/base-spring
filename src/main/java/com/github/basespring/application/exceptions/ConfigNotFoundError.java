package com.github.basespring.application.exceptions;

public class ConfigNotFoundError extends RuntimeException {
    public ConfigNotFoundError() {super();}

    public ConfigNotFoundError(String message) {
        super(message);
    }
}
