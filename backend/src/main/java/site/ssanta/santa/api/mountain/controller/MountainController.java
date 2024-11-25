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
import site.ssanta.santa.api.group.service.GroupService;
import site.ssanta.santa.api.group_participant.service.GroupParticipantService;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.domain.MountainPath;
import site.ssanta.santa.api.mountain.domain.MountainSpot;
import site.ssanta.santa.api.mountain.dto.*;
import site.ssanta.santa.api.mountain.service.MountainService;
import site.ssanta.santa.api.mountain.util.MountainExpCalculator;
import site.ssanta.santa.api.mountain_complete.domain.MountainComplete;
import site.ssanta.santa.api.mountain_complete.dto.MountainCompleteQueryResponseDto;
import site.ssanta.santa.api.mountain_complete.dto.MountainCompleteVO;
import site.ssanta.santa.api.mountain_complete.service.MountainCompleteService;
import site.ssanta.santa.api.mountain_like.service.MountainLikeService;
import site.ssanta.santa.common.exception.ExceptionResponse;
import site.ssanta.santa.common.jwt.JwtUtil;

import java.math.BigDecimal;
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
    private final MountainCompleteService mountainCompleteService;
//    private final MountainRAGService mountainRAGService;
    private final MountainExpCalculator expCalculator;
    private final JwtUtil jwtUtil;
    private final GroupParticipantService groupParticipantService;
    private final GroupService groupService;

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
                        .latitude(new BigDecimal(mountain.getLatitude()))
                        .longitude(new BigDecimal(mountain.getLongitude()))
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
        mountainLikeService.delete(userId, dto.getMountainId());

        return ResponseEntity.created(null).build();
    }

    @GetMapping("/detail")
    @Operation(summary = "산 상세 정보", description = "산의 상세 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = MountainDetailDto.class))),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "코드에 해당하는 산이 없는 경우")
    })
    public ResponseEntity<MountainDetailDto> getMountainInfo(@CookieValue(value = "access_token", required = false) String accessToken,
                                                             @RequestParam("mountainCode") String mountainCode) {
        Long userId = -1L;

        if (accessToken != null) {
            userId = jwtUtil.getUserId(accessToken);
        }

        MountainDetailDto response = mountainService.getMountainInfo(mountainCode, userId);
        MountainComplete mountainComplete = mountainCompleteService
                .findByMountainIdAndMemberId(response.getMountainInfo().getId(), userId);
        response.setIsCompletedToday(mountainComplete != null && mountainCompleteService.checkToday(mountainComplete));

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/paths")
    public ResponseEntity<List<MountainPath>> getMountainPaths(@RequestParam("mountainCode") String mountainCode) {
        return ResponseEntity.ok()
                .body(mountainService.getMountainPaths(mountainCode));
    }

    @GetMapping("/spots")
    public ResponseEntity<List<MountainSpot>> getMountainSpots(@RequestParam("mountainCode") String mountainCode) {
        return ResponseEntity.ok()
                .body(mountainService.getMountainSpots(mountainCode));
    }

    @GetMapping("/complete")
    @Operation(summary = "완등 정보 조회", description = "사용자가 완등한 산 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 완등한 산",
                    content = @Content(schema = @Schema(implementation = MountainCompleteQueryResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    ))
    })
    public ResponseEntity<?> findCompleteById(@RequestAttribute("userId") Long memberId) {
        Member member = memberService.getMemberById(memberId);
        List<MountainCompleteVO> completes = mountainCompleteService.getCompleteByMember(member);
        MountainCompleteQueryResponseDto response = new MountainCompleteQueryResponseDto(completes);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/complete")
    @Operation(summary = "완등", description = "완등 했을 떄")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "완등 했을 때",
                    content = @Content(schema = @Schema(implementation = MountainCompleteResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    ))
    })
    public ResponseEntity<?> complete(@RequestAttribute("userId") Long memberId,
                                      @RequestBody MountainLikeRequestDto dto) {
        Member member = memberService.getMemberById(memberId);
        Mountain mountain = mountainService.findById(dto.getMountainId());
        boolean result = mountainCompleteService.save(member, mountain);
        MountainCompleteResponseDto response;

        if (result) {
            int exp = expCalculator.calculateExp(mountain.getHeight());
            memberService.updateExp(member, exp);
            groupParticipantService.findAllByMemberId(memberId)
                    .forEach(groupParticipant ->
                            groupService.updateExp(groupParticipant.getGroupId(), exp));
            response = new MountainCompleteResponseDto(exp);
        } else {
            response = new MountainCompleteResponseDto(0);
        }

        return ResponseEntity.created(null).body(response);
    }

//    @PostMapping("/recommend")
//    public ResponseEntity<RouteRecommendation> recommendRoute(@RequestBody RouteRequest request) {
//        return ResponseEntity.ok()
//                        .body(mountainRAGService.recommendRoute(request));
//    }
}
