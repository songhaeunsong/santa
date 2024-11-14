package site.ssanta.santa.api.group_participant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "group_participants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "group_id")
    private Long groupId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "create_at")
    private Date createAt;
}
