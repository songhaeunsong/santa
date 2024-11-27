package site.ssanta.santa.api.appointment.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class MakeAppointmentRequestDto {

    private Long groupId;
    private Long mountainId;
    private Date date;
}
