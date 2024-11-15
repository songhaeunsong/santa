package site.ssanta.santa.api.appointment.dto;

import java.util.Date;

public interface AppointmentsVO {

    Long getId();
    Long getGroupId();
    Long getMountainId();
    Long adminId();
    Long getCountOfMembers();
    Date getMeetAt();
}
