package site.ssanta.santa.api.group.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@Table(name = "groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long exp;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "delete_at")
    private Date deleteAt;
}
