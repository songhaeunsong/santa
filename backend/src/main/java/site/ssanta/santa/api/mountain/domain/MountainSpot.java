package site.ssanta.santa.api.mountain.domain;

import com.mongodb.annotations.Immutable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "mountain_spots")
@Immutable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MountainSpot {

    @Id
    private String id;
    private FeatureAttributes attributes;
    private FeatureGeometry geometry;

    @Field("file_type")
    private String fileType;
}
