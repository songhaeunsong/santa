package site.ssanta.santa.common.jwt.exception;

import org.springframework.http.HttpStatus;
import site.ssanta.santa.common.exception.CustomException;

public class AuthCodeMissingException extends CustomException {

    public AuthCodeMissingException(String code) {
        super(HttpStatus.NOT_FOUND, code);
    }
}
