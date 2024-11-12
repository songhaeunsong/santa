package site.ssanta.santa.api.mountain.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mountains")
public class Mountain {

    @Id
    private Long id;

    private String name;
    private String province;
    private String city;
    private Integer height;
}
