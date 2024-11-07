package site.ssanta.santa.common.jwt.exception;

import org.springframework.http.HttpStatus;
import site.ssanta.santa.common.exception.CustomException;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException(String code) {
        super(HttpStatus.NOT_FOUND, code);
    }
}
