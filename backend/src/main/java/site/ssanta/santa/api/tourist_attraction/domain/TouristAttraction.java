package site.ssanta.santa.api.tourist_attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Getter
@Immutable
@Table(name = "tourist_attractions")
public class TouristAttraction {

    @Id
    @Column(name = "content_id")
    private Integer contentId;

    private String title;

    @Column(name = "first_image1")
    private String image;

    @Column(name = "map_level")
    private Integer mapLevel;

    @Column(precision = 20, scale = 17)
    private BigDecimal latitude;

    @Column(precision = 20, scale = 17)
    private BigDecimal longitude;

    @Column(name = "addr1")
    private String address;

    private String province;
    private String city;

    @Column(name = "type_id")
    private Integer typeId;
}
