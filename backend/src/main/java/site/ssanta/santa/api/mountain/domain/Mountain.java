package site.ssanta.santa.api.mountain.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

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
}
