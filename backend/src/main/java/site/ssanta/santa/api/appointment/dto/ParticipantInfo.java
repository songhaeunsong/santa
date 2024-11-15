package site.ssanta.santa.api.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParticipantInfo {

    private Long id;
    private String name;
}
