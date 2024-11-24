package site.ssanta.santa.api.rank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ssanta.santa.api.group.dto.GroupVO;
import site.ssanta.santa.api.group.service.GroupService;
import site.ssanta.santa.api.member.dto.MemberInfoVO;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.api.rank.dto.GroupRankResponseDto;
import site.ssanta.santa.api.rank.dto.IndividualRankResponseDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
@Tag(name = "Rank API", description = "Rank API")
public class RankController {

    private final MemberService memberService;
    private final GroupService groupService;

    @GetMapping("/individual")
    @Operation(summary = "개인 랭킹 조회", description = "개인 랭킹 조회, 티어 순, 경험치 순")
    @ApiResponse(responseCode = "200", description = "개인 랭킹 조회 결과",
    content = @Content(schema = @Schema(implementation = IndividualRankResponseDto.class)))
    public ResponseEntity<?> getIndividualRank() {
        List<MemberInfoVO> result = memberService.getMemberRank();
        IndividualRankResponseDto response = new IndividualRankResponseDto(result);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/group")
    @Operation(summary = "그룹 랭킹 조회", description = "그룹 랭킹 조회, 경험치 순, 생성 순")
    @ApiResponse(responseCode = "200", description = "그룹 랭킹 조회 결과",
            content = @Content(schema = @Schema(implementation = GroupRankResponseDto.class)))
    public ResponseEntity<?> getGroupRank() {
        List<GroupVO> result = groupService.getGroupRank();
        GroupRankResponseDto response = new GroupRankResponseDto(result);
        return ResponseEntity.ok().body(response);
    }
}
