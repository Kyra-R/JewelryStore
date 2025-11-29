package com.example.security.util;

public class ForbiddenLoginOrPasswordException extends RuntimeException{
    public ForbiddenLoginOrPasswordException() {
        super("Forbidden login!");
    }
}
