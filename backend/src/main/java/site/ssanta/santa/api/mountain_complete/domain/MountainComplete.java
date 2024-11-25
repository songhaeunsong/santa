package site.ssanta.santa.api.mountain_complete.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;

import java.util.Date;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "mountain_completes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder.Default
    @Column(name = "create_at")
    private Date createAt = new Date();

    @Column(name = "update_at")
    private Date updateAt;

    public void setUpdate() {
        this.updateAt = new Date();
    }
}
