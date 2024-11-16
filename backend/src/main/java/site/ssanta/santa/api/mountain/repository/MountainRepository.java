package site.ssanta.santa.api.mountain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.dto.MountainVO;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, Long> {

    List<MountainVO> findAllByProvinceContainingAndCityContaining(String province, String city);
}
