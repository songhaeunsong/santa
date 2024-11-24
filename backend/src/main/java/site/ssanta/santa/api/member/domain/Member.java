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

    public void updateExp(int exp) {
        long newExp = this.exp + exp;

        if (newExp >= 100) {
            upgradeTier();
            this.exp = newExp % 100;
        } else {
            this.exp = newExp;
        }
    }

    private void upgradeTier() {
        switch (this.tier) {
            case BRONZE:
                this.tier = Tier.SILVER;
                break;
            case SILVER:
                this.tier = Tier.GOLD;
                break;
            case GOLD:
                this.tier = Tier.DIAMOND;
                break;
            case DIAMOND:
                this.tier = Tier.CHALLENGER;
                break;
            case CHALLENGER:
                break;
        }
    }
}
