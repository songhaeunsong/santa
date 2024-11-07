package site.ssanta.santa.common.jwt.exception;

import org.springframework.http.HttpStatus;
import site.ssanta.santa.common.exception.CustomException;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException(String code) {
        super(HttpStatus.BAD_REQUEST, code);
    }
}
