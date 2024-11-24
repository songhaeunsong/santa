package site.ssanta.santa.api.rank.controller;

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
    public ResponseEntity<?> getIndividualRank() {
        List<MemberInfoVO> result = memberService.getMemberRank();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/group")
    public ResponseEntity<?> getGroupRank() {
        List<GroupVO> result = groupService.getGroupRank();
        return ResponseEntity.ok().body(result);
    }
}
