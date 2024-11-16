package site.ssanta.santa.api.mountain.controller;

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
import site.ssanta.santa.api.mountain.dto.MountainFilterResponseDto;
import site.ssanta.santa.api.mountain.dto.MountainVO;
import site.ssanta.santa.api.mountain.service.MountainService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mountain")
@Tag(name = "Mountain API", description = "Mountain API")
public class MountainController {

    private final MountainService mountainService;

    @GetMapping()
    @Operation(summary = "산 정보 조회", description = "조건에 맞는 산 정보 조회")
    @ApiResponse(responseCode = "200", description = "사용자가 검색한 조건에 맞는 산",
    content = @Content(schema = @Schema(implementation = MountainFilterResponseDto.class)))
    public ResponseEntity<?> findByCondition(@RequestParam("province") String province,
                                             @RequestParam("city") String city) {
        List<MountainVO> result = mountainService.findByCondition(province, city);
        MountainFilterResponseDto response = new MountainFilterResponseDto(result);
        return ResponseEntity.ok().body(response);
    }
}
