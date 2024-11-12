package site.ssanta.santa.api.group.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
@Tag(name = "Group API", description = "Group API")
public class GroupController {

    @GetMapping()
    public ResponseEntity<?> getGroups() {
        return ResponseEntity.ok().build();
    }
}


