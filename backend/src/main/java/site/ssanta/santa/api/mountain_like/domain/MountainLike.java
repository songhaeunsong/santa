package site.ssanta.santa.api.mountain_like.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "mountain_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MountainLike {

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
