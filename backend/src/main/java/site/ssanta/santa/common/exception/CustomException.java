package site.ssanta.santa.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {

    private HttpStatus status;
    private String code;

    public CustomException(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }
}
