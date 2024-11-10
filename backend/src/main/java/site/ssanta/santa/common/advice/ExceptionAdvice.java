package site.ssanta.santa.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.ssanta.santa.common.exception.CustomException;
import site.ssanta.santa.common.exception.ExceptionResponse;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleException(CustomException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(e.getStatus().value())
                .code(e.getCode())
                .build();

        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
}
