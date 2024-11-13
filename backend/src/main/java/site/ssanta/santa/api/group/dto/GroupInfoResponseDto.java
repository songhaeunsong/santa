package site.ssanta.santa.api.group.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInfoResponseDto {

    private Long id;
    private String name;
    private Long exp;
    private Long adminId;
    private String adminName;
    private String description;
    private Long countOfMembers;
    private Boolean isAdmin;
    private Boolean isMember;

    @ArraySchema(schema = @Schema(implementation = ParticipantInfo.class))
    private List<ParticipantInfo> participants;
}
