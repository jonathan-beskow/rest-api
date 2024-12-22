package jb.api_gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long servialVersionUID = 1L;

    public ResourceNotFoundException(String s) {
        super(s);
    }
}