package site.ssanta.santa.api.group.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
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
