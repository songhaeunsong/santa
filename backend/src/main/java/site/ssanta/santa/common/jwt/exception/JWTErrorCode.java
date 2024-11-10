package site.ssanta.santa.common.jwt.exception;

import lombok.Getter;

@Getter
public enum JWTErrorCode {

    ERR_MISSING_AUTHORIZATION_CODE,
    ERR_MISSING_ACCESS_TOKEN,
    ERR_MISSING_REFRESH_TOKEN,
    ERR_ACCESS_TOKEN_EXPIRED,
    ERR_REFRESH_TOKEN_EXPIRED,
    ERR_INVALID_TOKEN,
    ERR_NOT_FOUND_MEMBER,
}
