package site.ssanta.santa.api.mountain.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeatureAttributes {

    @Field("MNTN_CODE")
    private String mountainCode;

    @Field("MNTN_NM")
    private String mountainName;

    @Field("PMNTN_NM")
    private String pathName;

    @Field("PMNTN_DFFL")
    private String difficulty;
}
