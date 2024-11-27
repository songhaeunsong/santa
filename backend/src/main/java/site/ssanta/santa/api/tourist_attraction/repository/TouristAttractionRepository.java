package site.ssanta.santa.api.tourist_attraction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.tourist_attraction.domain.TouristAttraction;
import site.ssanta.santa.api.tourist_attraction.dto.TouristAttractionVO;

import java.util.List;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Integer> {

    List<TouristAttractionVO> findAllByProvinceAndCityAndTypeId(String province, String city, Integer typeId);
}
