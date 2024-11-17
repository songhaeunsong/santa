package site.ssanta.santa.api.tourist_attraction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.tourist_attraction.dto.TouristAttractionVO;
import site.ssanta.santa.api.tourist_attraction.repository.TouristAttractionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;

    public List<TouristAttractionVO> findByConditions(Integer type, String province, String city) {
        return touristAttractionRepository.findAllByProvinceAndCityAndTypeId(province, city, type);
    }
}
