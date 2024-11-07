package site.ssanta.santa.common.jwt.exception;

import org.springframework.http.HttpStatus;
import site.ssanta.santa.common.exception.CustomException;

public class JwtExpiredException extends CustomException {

    public JwtExpiredException(String code) {
        super(HttpStatus.UNAUTHORIZED, code);
    }
}
