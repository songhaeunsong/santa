package site.ssanta.santa.api.mountain_complete.domain;

import jakarta.persistence.*;
import lombok.Getter;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;

@Getter
@Entity
@Table(name = "mountain_completes")
public class MountainComplete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;
}
