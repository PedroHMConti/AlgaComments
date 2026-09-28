package com.algaworks.CommentService.api.client;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
@NoArgsConstructor
public class ModerationClientException extends RuntimeException {
    public ModerationClientException(String message) {
        super(message);
    }
}
