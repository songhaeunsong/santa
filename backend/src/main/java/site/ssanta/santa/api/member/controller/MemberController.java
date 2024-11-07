package site.ssanta.santa.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import site.ssanta.santa.api.member.dto.*;
import site.ssanta.santa.api.member.service.OauthService;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.common.jwt.exception.AuthCodeMissingException;
import site.ssanta.santa.common.jwt.exception.JWTErrorCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "User API", description = "User API")
public class MemberController {

    private final OauthService oauthService;
    private final MemberService memberService;

    @PostMapping("/auth")
    @Operation(summary = "로그인/회원가입",
            description = "인가 코드를 이용한 로그인/회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    headers = {
                            @Header(name = HttpHeaders.SET_COOKIE, description = "Access Token",
                                    schema = @Schema(type = "string",
                                            example = "access_token=xxxx; Path=/; Max-Age=30분; HttpOnly; SameSite=None")),
                            @Header(name = HttpHeaders.SET_COOKIE2, description = "Refresh Token",
                                    schema = @Schema(type = "string",
                                            example = "refresh_token=yyyy; Path=/; Max-Age=7일; HttpOnly; SameSite=None"))
                    }, content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Boolean.class, description = "새로운 회원이면 true, 아니면 false")
            )),

            @ApiResponse(responseCode = "400", description = "코드가 누락된 경우")
    })
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDto dto) {
        if (dto.getCode() == null) {
            throw new AuthCodeMissingException(JWTErrorCode.ERR_MISSING_AUTHORIZATION_CODE.toString());
        }

        log.debug("code: {}", dto.getCode());
        String accessToken = oauthService.getAccessToken(dto.getCode());
        log.debug("access token: {}", accessToken);
        KakaoUserInfoResponseDto userInfo = oauthService.getUserInfo(accessToken);
        LoginResponseDto result = memberService.getToken(userInfo);

        ResponseCookie access = ResponseCookie
                .from("access_token", result.getAccessToken())
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(1000 * 60 * 30)
                .build();

        ResponseCookie refresh = ResponseCookie
                .from("refresh_token", result.getRefreshToken())
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(1000 * 60 * 60 * 24 * 7)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, access.toString())
                .header(HttpHeaders.SET_COOKIE, refresh.toString())
                .body(result.getIsNew());
    }

    @GetMapping("/mypage")
    @Tag(name = "사용자 정보 조회", description = "Token을 이용한 사용자 정보 조회(미완성)")
    public ResponseEntity<?> getUserInfo(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        log.debug("token: {}", token);
        VerifyTokenResponseDto response = memberService.getNickname(token);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/reissue")
    @Tag(name = "토큰 재발급", description = "Refresh Token을 이용한 Access Token 재발급(미완성)")
    public ResponseEntity<?> reissue(
            @RequestHeader(value = "X-Refresh", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        log.debug("token: {}", token);
        ReIssueTokenDto response = memberService.reissueToken(token);
        return ResponseEntity.ok().body(response);
    }
}

