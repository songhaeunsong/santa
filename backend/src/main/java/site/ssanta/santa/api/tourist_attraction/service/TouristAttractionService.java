package site.ssanta.santa.api.tourist_attraction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.tourist_attraction.dto.TouristAttractionVO;
import site.ssanta.santa.api.tourist_attraction.repository.TouristAttractionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;

    public List<TouristAttractionVO> findByConditions(Integer type, List<String> province, List<String> city) {
        List<TouristAttractionVO> result = new ArrayList<>();
        for (String pr : province) {
            for (String ct : city) {
               result.addAll(touristAttractionRepository.findAllByProvinceAndCityAndTypeId(pr, ct, type));
            }
        }
        return result;
    }
}
