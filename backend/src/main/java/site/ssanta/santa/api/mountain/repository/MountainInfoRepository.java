package site.ssanta.santa.api.mountain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import site.ssanta.santa.api.mountain.domain.MountainInfo;

import java.util.Optional;

public interface MountainInfoRepository extends MongoRepository<MountainInfo, String> {

    Optional<MountainInfo> findByMountainCode(String mountainCode);
}
