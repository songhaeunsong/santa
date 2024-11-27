package site.ssanta.santa.api.mountain.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "mountain_info")
@Getter
public class MountainInfo {

    @Id
    private String id;

    @Field("MNTN_CODE")
    private String mountainCode;

    @Field("MNTN_NM")
    private String mountainName;
}
