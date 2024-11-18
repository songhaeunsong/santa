package site.ssanta.santa.api.mountain.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import site.ssanta.santa.api.mountain_like.domain.MountainLike;

import java.util.List;

@Entity
@Getter
@Immutable
@AllArgsConstructor
@Table(name = "mountains")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mountain {

    @Id
    private Long id;

    private String name;
    private String province;
    private String city;
    private Integer height;

    @Column(name = "lat")
    private String latitude;

    @Column(name = "lon")
    private String longitude;

    private String image;

    @OneToMany(mappedBy = "mountain")
    private List<MountainLike> mountainLikes;
}
