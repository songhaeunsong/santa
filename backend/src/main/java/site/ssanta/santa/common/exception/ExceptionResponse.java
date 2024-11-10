package site.ssanta.santa.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {

    private Integer status;
    private String code;

    public ExceptionResponse(Integer status, String code) {
        this.status = status;
        this.code = code;
    }
}
