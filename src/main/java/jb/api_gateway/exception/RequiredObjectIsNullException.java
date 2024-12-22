package jb.api_gateway.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{

    @Serial
    private static final long servialVersionUID = 1L;

    public RequiredObjectIsNullException(String s) {
        super(s);
    }
    public RequiredObjectIsNullException() {
        super("It's not allowed to persist a null object");
    }
}