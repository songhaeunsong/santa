package site.ssanta.santa.api.group.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.ssanta.santa.api.group.dto.AllGroupsResponseDto;
import site.ssanta.santa.api.group.dto.GroupVO;
import site.ssanta.santa.api.group.service.GroupService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
@Tag(name = "Group API", description = "Group API")
public class GroupController {

    private final GroupService groupService;

    @GetMapping()
    @Operation(summary = "전체 조회", description = "전체 그룹 정보 반환")
    @ApiResponse(responseCode = "200", description = "전체 결과 반환",
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
    public ResponseEntity<?> getGroupInfo(@RequestParam("id") Long groupId) {
        groupService.findById(groupId);
        return ResponseEntity.ok().build();
    }
}


