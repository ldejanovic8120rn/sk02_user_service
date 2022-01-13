package com.sk02.sk02_user_service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class RankException extends HttpResponseException{

    public RankException(String message) {
        super(message);
    }
}
