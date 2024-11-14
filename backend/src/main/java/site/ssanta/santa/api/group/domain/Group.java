package site.ssanta.santa.api.group.domain;

import jakarta.persistence.*;
import lombok.*;
import site.ssanta.santa.api.member.dto.MemberInfoVO;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "`groups`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long exp;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(length = 100)
    private String description;

    @Column(name = "count_of_members")
    private Long countOfMembers;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "delete_at")
    private Date deleteAt;

    public void joinMember(MemberInfoVO member) {
        this.exp += member.getExp();
        this.countOfMembers += 1;
    }
}
