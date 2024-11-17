package site.ssanta.santa.api.member.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.mountain_complete.domain.MountainComplete;
import site.ssanta.santa.api.mountain_like.domain.MountainLike;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private Long id;

    private String email;

    private String nickname;

    private String uuid;

    @Column(name= "profile_url", length = 1000)
    private String profileUrl;

    @Enumerated(value = EnumType.STRING)
    private Tier tier;

    private Long exp;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "delete_at")
    private Date deleteAt;

    @OneToMany(mappedBy = "member")
    private List<MountainLike> mountainLikes;

    @OneToMany(mappedBy = "member")
    private List<MountainComplete> mountainCompletes;

    public void updateNickname(String nickname) {
        if (nickname != null) {
            this.nickname = nickname;
        }
    }
}
