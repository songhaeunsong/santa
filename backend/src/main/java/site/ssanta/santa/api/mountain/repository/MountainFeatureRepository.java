package site.ssanta.santa.api.mountain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import site.ssanta.santa.api.mountain.domain.MountainFeature;

import java.util.List;

public interface MountainFeatureRepository extends MongoRepository<MountainFeature, String> {

    List<MountainFeature> findByFileTypeAndAttributesMountainCode(String fileType, String mountainCode);
}
