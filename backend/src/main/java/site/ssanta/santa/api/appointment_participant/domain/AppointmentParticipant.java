package site.ssanta.santa.api.appointment_participant.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.group_participant.domain.Role;

@Entity
@Builder
@Getter
@AllArgsConstructor
@Table(name = "appointment_participants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "member_id")
    private Long memberId;

    private Role role;
}
