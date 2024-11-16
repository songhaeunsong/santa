package site.ssanta.santa.api.mountain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.dto.MountainVO;
import site.ssanta.santa.api.mountain.repository.MountainRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;


    public List<MountainVO> findByCondition(String province, String city) {
        return mountainRepository.findAllByProvinceContainingAndCityContaining(province, city);
    }

    public Mountain findById(Long mountainId) {
        return mountainRepository.findById(mountainId)
                .orElseThrow();
    }
}
