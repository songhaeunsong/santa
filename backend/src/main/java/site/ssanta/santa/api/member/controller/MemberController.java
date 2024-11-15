package site.ssanta.santa.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import site.ssanta.santa.api.group.service.GroupService;
import site.ssanta.santa.api.group_participant.service.GroupParticipantService;
import site.ssanta.santa.api.member.dto.*;
import site.ssanta.santa.api.member.service.OauthService;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.common.exception.ExceptionResponse;
import site.ssanta.santa.common.jwt.JwtUtil;
import site.ssanta.santa.common.jwt.exception.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member API", description = "Member API")
public class MemberController {

    private final OauthService oauthService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth")
    @Operation(summary = "로그인/회원가입", description = "인가 코드를 이용한 로그인/회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    headers = {
                            @Header(name = HttpHeaders.SET_COOKIE, description = "Access Token",
                                    schema = @Schema(type = "string",
                                            example = "access_token=...; Path=/;Max-Age=30분;HttpOnly;SameSite=None")),
                            @Header(name = HttpHeaders.SET_COOKIE2, description = "Refresh Token",
                                    schema = @Schema(type = "string",
                                            example = "refresh_token=...; Path=/;Max-Age=7일;HttpOnly;SameSite=None"))
                    }, content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LoginResponseDto.class, description = "신규 회원이면 true")
            )),
            @ApiResponse(responseCode = "400", description = "코드가 누락된 경우", content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class)
            ))
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
                .secure(true)
                .path("/")
                .maxAge(1000 * 60 * 30)
                .build();

        ResponseCookie refresh = ResponseCookie
                .from("refresh_token", result.getRefreshToken())
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
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
    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공", content = @Content(
            schema = @Schema(implementation = MemberInfoVO.class))
    )
    public ResponseEntity<?> getMyInfo(@RequestAttribute("userId") Long userId) {
        log.debug("userId: {}", userId);
        MemberInfoVO memberInfo = memberService.getUserInfo(userId);
        return ResponseEntity.ok()
                .body(memberInfo);
    }

    @GetMapping("/profile")
    @Operation(summary = "다른 사용자 정보 조회", description = " 다른 사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공", content = @Content(
                    schema = @Schema(implementation = MemberProfileResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
            )),
    })
    public ResponseEntity<?> getUserInfo(@RequestParam("id") Long userId,
                                         @CookieValue(value = "access_token", required = false) String accessToken) {
        Long currentMember = -1L;
        if (accessToken != null) {
            currentMember = jwtUtil.getUserId(accessToken);
        }

        MemberInfoVO memberInfo = memberService.getUserInfo(userId);
        MemberProfileResponseDto result = MemberProfileResponseDto.builder()
                .exp(memberInfo.getExp())
                .tier(memberInfo.getTier())
                .email(memberInfo.getEmail())
                .nickname(memberInfo.getNickname())
                .profileUrl(memberInfo.getProfileUrl())
                .isSelf(Objects.equals(currentMember, userId))
                .build();

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/reissue")
    @SecurityRequirement(name = "REFRESH")
    @Operation(summary = "토큰 재발급", description = "Refresh Token을 이용한 Access Token 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재발급 성공",
                    headers = {
                            @Header(name = HttpHeaders.SET_COOKIE, description = "Access Token",
                                    schema = @Schema(type = "string",
                                            example = "access_token=...;Path=/;Max-Age=30분;HttpOnly;SameSite=None")),
                    }, content = @Content(
            )),
            @ApiResponse(responseCode = "400", description = "refresh token이 누락되거나 지원하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "refresh token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    )),
    })
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new InvalidJwtTokenException(JWTErrorCode.ERR_INVALID_TOKEN.name());
        }

        String accessToken = memberService.reissueToken(refreshToken);
        ResponseCookie access = ResponseCookie
                .from("access_token", accessToken)
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(1000 * 60 * 30)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, access.toString())
                .build();
    }

    @GetMapping("/check-nickname")
    @Operation(summary = "닉네임 중복 확인")
    @ApiResponse(responseCode = "200", description = "닉네임 중복 여부",
            content = @Content(
                    schema = @Schema(implementation = CheckNicknameResponseDto.class, description = "중복인 경우 true"))
    )
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        CheckNicknameResponseDto result = memberService.checkNickname(nickname);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/nickname")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "닉네임 설정", description = "최초 로그인 시 닉네임 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공"),
            @ApiResponse(responseCode = "404", description = "해당하는 사용자 없음",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionResponse.class)
                    ))
    })
    public ResponseEntity<?> setNickname(@RequestAttribute("userId") Long userId,
                                         @RequestBody SetNicknameDto dto) {
        log.info("{}", "set nickname");
        log.info("nickname: {}", dto.getNickname());
        memberService.setNickname(userId, dto);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    public ResponseEntity<?> logout() {
        ResponseCookie access = ResponseCookie
                .from("access_token", "")
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie refresh = ResponseCookie
                .from("refresh_token", "")
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, access.toString())
                .header(HttpHeaders.SET_COOKIE, refresh.toString())
                .build();
    }

    @GetMapping("/active")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "로그인 상태 확인", description = "사용자의 로그인 상태 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 상태"),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "코드가 누락된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
            ))
    })
    public ResponseEntity<?> getActive() {
        return ResponseEntity.ok().build();
    }
}

