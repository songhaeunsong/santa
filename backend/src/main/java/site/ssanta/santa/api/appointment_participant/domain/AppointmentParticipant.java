package site.ssanta.santa.api.appointment_participant.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.group_participant.domain.Role;
import site.ssanta.santa.api.member.domain.Member;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Role role;
}
