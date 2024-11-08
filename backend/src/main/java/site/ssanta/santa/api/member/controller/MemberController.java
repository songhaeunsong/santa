package site.ssanta.santa.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
import site.ssanta.santa.common.jwt.exception.MemberNotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "User API", description = "User API")
public class MemberController {

    private final OauthService oauthService;
    private final MemberService memberService;

    @PostMapping("/auth")
    @Operation(summary = "로그인/회원가입", description = "인가 코드를 이용한 로그인/회원가입")
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
                    schema = @Schema(implementation = LoginResponseDto.class, description = "새로운 회원이면 true, 아니면 false")
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
                .body(result);
    }

    @GetMapping("/mypage")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "사용자 정보 조회", description = "Token을 이용한 사용자 정보 조회")
    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공")
    public ResponseEntity<?> getUserInfo(@RequestAttribute("userId") Long userId) {
        log.debug("userId: {}", userId);
        UserInfoVO userInfo = memberService.getUserInfo(userId);
        return ResponseEntity.ok().body(userInfo);
    }

    @GetMapping("/reissue")
    @SecurityRequirement(name = "REFRESH")
    @Operation(summary = "토큰 재발급", description = "Refresh Token을 이용한 Access Token 재발급(미완성)")
    public ResponseEntity<?> reissue(@RequestAttribute("userId") Long userId) {
        String accessToken = memberService.reissueToken(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-name")
    @Operation(summary = "닉네임 중복 확인")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        CheckNicknameResponseDto result = memberService.checkNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/nickname")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "닉네임 설정", description = "최초 로그인 시 닉네임 설정")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공"),
            @ApiResponse(responseCode = "404", description = "해당하는 사용자 없음", content = @Content(
                    schema = @Schema(implementation = MemberNotFoundException.class)
            ))
    }
    )
    public ResponseEntity<?> setNickname(@RequestAttribute("userId") Long userId,
                                         @RequestBody SetNicknameDto dto) {
        memberService.setNickname(userId, dto);
        return ResponseEntity.ok().build();
    }
}

