package site.ssanta.santa.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

//@Entity
//@Table(name = "members")
public class Member {

    @Id
    private Long id;

    @Column(name = "create_at")
    private Date createAt;
}
