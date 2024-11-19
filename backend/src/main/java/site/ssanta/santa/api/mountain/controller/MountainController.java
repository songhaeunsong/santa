package site.ssanta.santa.api.mountain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.dto.MountainFilterResponseDto;
import site.ssanta.santa.api.mountain.dto.MountainLikeRequestDto;
import site.ssanta.santa.api.mountain.dto.MountainQueryResponseDto;
import site.ssanta.santa.api.mountain.service.MountainService;
import site.ssanta.santa.api.mountain_like.service.MountainLikeService;
import site.ssanta.santa.common.exception.ExceptionResponse;
import site.ssanta.santa.common.jwt.JwtUtil;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mountain")
@Tag(name = "Mountain API", description = "Mountain API")
public class MountainController {

    private final MountainService mountainService;
    private final MemberService memberService;
    private final MountainLikeService mountainLikeService;
    private final JwtUtil jwtUtil;

    @GetMapping()
    @Operation(summary = "산 정보 조회", description = "조건에 맞는 산 정보 조회")
    @ApiResponse(responseCode = "200", description = "사용자가 검색한 조건에 맞는 산",
            content = @Content(schema = @Schema(implementation = MountainFilterResponseDto.class)))
    public ResponseEntity<?> findByCondition(@CookieValue(value = "access_token", required = false) String accessToken,
                                             @RequestParam("province") String province,
                                             @RequestParam("city") String city) {
        Long userId;
        if (accessToken != null) {
            userId = jwtUtil.getUserId(accessToken);
        } else {
            userId = -1L;
        }

        List<Mountain> mountains = mountainService.findByCondition(province, city);
        log.debug("size: {}", mountains.size());
        List<MountainQueryResponseDto> result = mountains.stream()
                .map(mountain -> MountainQueryResponseDto.builder()
                        .id(mountain.getId())
                        .name(mountain.getName())
                        .province(mountain.getProvince())
                        .city(mountain.getCity())
                        .height(mountain.getHeight())
                        .latitude(mountain.getLatitude())
                        .longitude(mountain.getLongitude())
                        .image(mountain.getImage())
                        .isLiked(mountain.getMountainLikes()
                                .stream()
                                .anyMatch(mountainLike -> mountainLike.getMember().getId().equals(userId)))
                        .build())
                .toList();

        MountainFilterResponseDto response = new MountainFilterResponseDto(result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/like")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "관심 산 등록", description = "사용자가 관심 산을 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "등록 완료"),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "해당하는 산이 없는 경우")
    })
    public ResponseEntity<?> likeMountain(@RequestAttribute("userId") Long userId,
                                          @RequestBody MountainLikeRequestDto dto) {
        Member member = memberService.getMemberById(userId);
        Mountain mountain = mountainService.findById(dto.getMountainId());
        mountainLikeService.save(member, mountain);

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/like")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "관심 산 등록 해제", description = "사용자가 관심 산을 등록 해제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "해제 완료"),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "좋아요한 적이 없는 경우")
    })
    public ResponseEntity<?> unlikeMountain(@RequestAttribute("userId") Long userId,
                                          @RequestBody MountainLikeRequestDto dto) {
        Member member = memberService.getMemberById(userId);
        Mountain mountain = mountainService.findById(dto.getMountainId());
        mountainLikeService.save(member, mountain);

        return ResponseEntity.created(null).build();
    }
}
