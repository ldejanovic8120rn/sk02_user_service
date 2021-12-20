package com.sk02.sk02_user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends HttpResponseException{

    public NotFoundException(String message) {
        super(message);
    }
}
