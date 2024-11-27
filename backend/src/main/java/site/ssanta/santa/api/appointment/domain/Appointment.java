package site.ssanta.santa.api.appointment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import site.ssanta.santa.api.appointment_participant.domain.AppointmentParticipant;
import site.ssanta.santa.api.member.domain.Member;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long groupId;

    @Column(name = "mountain_id")
    private Long mountainId;

    @Column(name = "admin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public void join() {
        this.countOfMembers += 1L;
    }

    public void delete() {
        this.countOfMembers -= 1L;
    }
}
