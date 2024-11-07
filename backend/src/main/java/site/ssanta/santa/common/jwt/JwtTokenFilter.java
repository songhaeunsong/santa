package site.ssanta.santa.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import site.ssanta.santa.common.exception.CustomException;
import site.ssanta.santa.common.jwt.exception.InvalidJwtTokenException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN = "access_token";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = null;
            Cookie[] cookies = request.getCookies();
            token = getTokenFromCookie(cookies);

            if (token == null) {
                throw new InvalidJwtTokenException(HttpStatus.BAD_REQUEST.toString());
            }

            Long id = jwtUtil.getUserId(token);
            request.setAttribute("userId", id);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            jwtExceptionHandler(response, e);
        }
    }

    private static String getTokenFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void jwtExceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> error = new HashMap<>();

        if (e instanceof CustomException customException) {
            response.setStatus(customException.getStatus().value());
            error.put("status", customException.getStatus().value());
            error.put("code", customException.getCode());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.put("code", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
