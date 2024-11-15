package site.ssanta.santa.api.appointment.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentResponseDto {

    private Long id;
    private Long countOfMembers;
    private Date meetAt;
    private String mountainName;
    private Boolean isMember;
    private Boolean isAdmin;

    @ArraySchema(schema = @Schema(implementation = ParticipantInfo.class))
    private List<ParticipantInfo> members;
}
