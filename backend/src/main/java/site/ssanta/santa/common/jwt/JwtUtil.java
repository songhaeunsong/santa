package site.ssanta.santa.common.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import site.ssanta.santa.api.member.dto.LoginResponseDto;
import site.ssanta.santa.common.jwt.exception.InvalidJwtTokenException;
import site.ssanta.santa.common.jwt.exception.JWTErrorCode;
import site.ssanta.santa.common.jwt.exception.JwtExpiredException;
import site.ssanta.santa.common.jwt.exception.MemberNotFoundException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    public JwtUtil(@Value("${SECRET.KEY}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public Long getUserId(String token) {
        String id = null;

        try {
            id = Jwts.parser()
                    .verifyWith(getSign())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException(HttpStatus.UNAUTHORIZED.toString());
        } catch (JwtException e) {
            throw new InvalidJwtTokenException(HttpStatus.BAD_REQUEST.toString());
        }

        if (id == null) {
            throw new MemberNotFoundException(JWTErrorCode.ERR_NOT_FOUND_MEMBER.toString());
        }

        return Long.parseLong(id);
    }

    private void validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSign())
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException(HttpStatus.UNAUTHORIZED.toString());
        } catch (JwtException e) {
            throw new InvalidJwtTokenException(HttpStatus.BAD_REQUEST.toString());
        }
    }

    public LoginResponseDto getTokens(Long userId) {
        String id = String.valueOf(userId);
        return LoginResponseDto.builder()
                .accessToken(generateToken(id, "ACCESS", ACCESS_TOKEN_EXPIRE_TIME))
                .refreshToken(generateToken(id, "REFRESH", REFRESH_TOKEN_EXPIRE_TIME))
                .build();
    }

    private String generateToken(String userId, String type, long expireTime) {
        return Jwts.builder()
                .subject(userId)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSign())
                .compact();
    }

    private SecretKey getSign() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String reissue(String token) {
        try {
            String id = Jwts.parser()
                    .verifyWith(getSign())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return generateToken(id, "ACCESS", ACCESS_TOKEN_EXPIRE_TIME);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException(HttpStatus.UNAUTHORIZED.toString());
        } catch (JwtException e) {
            throw new InvalidJwtTokenException(HttpStatus.BAD_REQUEST.toString());
        }
    }
}
