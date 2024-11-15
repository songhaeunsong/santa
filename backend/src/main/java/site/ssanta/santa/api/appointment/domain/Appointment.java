package site.ssanta.santa.api.appointment.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.appointment_participant.domain.AppointmentParticipant;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "appointments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "mountain_id")
    private Long mountainId;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "mountain_name")
    private String mountainName;

    @Builder.Default
    @Column(name = "count_of_members")
    private Long countOfMembers = 1L;

    @Column(name = "meet_at")
    private Date meetAt;

    @OneToMany(mappedBy = "appointment")
    private List<AppointmentParticipant> participants;
}
