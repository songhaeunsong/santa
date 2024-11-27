package site.ssanta.santa.api.mountain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import site.ssanta.santa.api.mountain.domain.MountainPath;
import site.ssanta.santa.api.mountain.domain.MountainSpot;

import java.util.List;

public interface MountainFeatureRepository extends MongoRepository<MountainPath, String> {

    @Query("{ 'attributes.MNTN_CODE': ?0 }")
    List<MountainPath> findMountainPathsByAttributes_MountainCode(String mountainCode);

    @Query("{ 'attributes.MNTN_CODE': ?0 }")
    List<MountainSpot> findMountainSpotsByAttributes_MountainCode(String mountainCode);
}
