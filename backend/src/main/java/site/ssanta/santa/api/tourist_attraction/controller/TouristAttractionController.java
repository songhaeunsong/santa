package site.ssanta.santa.api.tourist_attraction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.ssanta.santa.api.tourist_attraction.dto.TouristAttractionVO;
import site.ssanta.santa.api.tourist_attraction.dto.TouristQueryResponseDto;
import site.ssanta.santa.api.tourist_attraction.service.TouristAttractionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attraction")
@Tag(name = "Attraction API", description = "Attraction API")
public class TouristAttractionController {

    private final TouristAttractionService touristAttractionService;

    @GetMapping()
    @Operation(summary = "부가 정보 조회", description = "type 12; 관광지, 32; 숙박, 39; 음식점")
    @ApiResponse(responseCode = "200", description = "해당하는 정보 반환",
    content = @Content(schema = @Schema(implementation = TouristQueryResponseDto.class)))
    public ResponseEntity<?> getAttractionList(@RequestParam("type") Integer type,
                                               @RequestParam("province") String province,
                                               @RequestParam("city") String city) {
        List<TouristAttractionVO> list = touristAttractionService.findByConditions(type, province, city);
        TouristQueryResponseDto dto = new TouristQueryResponseDto(list);

        return ResponseEntity.ok().body(dto);
    }
}
