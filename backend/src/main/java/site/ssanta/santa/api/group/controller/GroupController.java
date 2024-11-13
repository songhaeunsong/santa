package site.ssanta.santa.api.group.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ssanta.santa.api.group.dto.AllGroupsResponseDto;
import site.ssanta.santa.api.group.dto.GroupInfoResponseDto;
import site.ssanta.santa.api.group.dto.GroupVO;
import site.ssanta.santa.api.group.dto.ParticipantInfo;
import site.ssanta.santa.api.group.service.GroupService;
import site.ssanta.santa.api.group_participant.domain.Role;
import site.ssanta.santa.api.group_participant.dto.ParticipantVO;
import site.ssanta.santa.api.group_participant.service.GroupParticipantService;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.common.exception.ExceptionResponse;
import site.ssanta.santa.common.jwt.JwtUtil;
import site.ssanta.santa.common.jwt.exception.JWTErrorCode;
import site.ssanta.santa.common.jwt.exception.MemberNotFoundException;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
@Tag(name = "Group API", description = "Group API")
public class GroupController {

    private final GroupService groupService;
    private final GroupParticipantService groupParticipantService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @GetMapping()
    @Operation(summary = "전체 조회", description = "전체 그룹 정보 반환")
    @ApiResponse(responseCode = "200", description = "전체 정보 조회 성공",
            content = @Content(schema = @Schema(implementation = AllGroupsResponseDto.class)))
    public ResponseEntity<?> getGroups() {
        List<GroupVO> groups = groupService.getAllGroups();
        AllGroupsResponseDto result = AllGroupsResponseDto.builder()
                .groups(groups)
                .build();

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/detail")
    @Operation(summary = "단건 조회", description = "ID에 해당하는 그룹 정보 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(
                    schema = @Schema(implementation = GroupInfoResponseDto.class)
            )),
            @ApiResponse(responseCode = "404", description = "ID에 해당하는 그룹 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
            )),
    })
    public ResponseEntity<?> getGroupInfo(@RequestParam("id") Long groupId,
                                          @CookieValue(value = "access_token", required = false) String accessToken) {
        Long currentUser = -1L;
        if (accessToken != null) {
            currentUser = jwtUtil.getUserId(accessToken);
        }

        GroupVO group = groupService.findById(groupId);
        List<ParticipantVO> participantVOList = groupParticipantService.findByGroupId(groupId);
        List<ParticipantInfo> participants = participantVOList.stream()
                .map(participant -> ParticipantInfo.builder()
                        .memberId(participant.getMemberId())
                        .role(participant.getRole())
                        .nickname(memberService.getUserInfo(participant.getMemberId()).getNickname())
                        .build())
                .toList();
        ParticipantInfo admin = participants.stream()
                .filter(participant -> participant.getRole().equals(Role.ADMIN))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException(JWTErrorCode.ERR_NOT_FOUND_MEMBER.name()));

        GroupInfoResponseDto result = GroupInfoResponseDto.builder()
                .id(groupId)
                .exp(group.getExp())
                .name(group.getName())
                .participants(participants)
                .adminId(group.getAdminId())
                .adminName(admin.getNickname())
                .description(group.getDescription())
                .countOfMembers(group.getCountOfMembers())
                .isAdmin(Objects.equals(group.getAdminId(), currentUser))
                .build();

        return ResponseEntity.ok()
                .body(result);
    }
}


