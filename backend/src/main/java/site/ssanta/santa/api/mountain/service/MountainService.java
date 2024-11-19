package site.ssanta.santa.api.mountain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.domain.MountainFeature;
import site.ssanta.santa.api.mountain.domain.MountainInfo;
import site.ssanta.santa.api.mountain.dto.MountainDto;
import site.ssanta.santa.api.mountain.repository.MountainFeatureRepository;
import site.ssanta.santa.api.mountain.repository.MountainInfoRepository;
import site.ssanta.santa.api.mountain.repository.MountainRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;
    private final MountainInfoRepository mountainInfoRepository;
    private final MountainFeatureRepository mountainFeatureRepository;


    public List<Mountain> findByCondition(String province, String city) {
        return mountainRepository.findAllByProvinceContainingAndCityContaining(province, city);
    }

    public Mountain findById(Long mountainId) {
        return mountainRepository.findById(mountainId)
                .orElseThrow();
    }

    public MountainDto getMountainInfo(String mountainCode) {
        MountainInfo mountainInfo = mountainInfoRepository.findByMountainCode(mountainCode)
                .orElseThrow();

        List<MountainFeature> paths = mountainFeatureRepository
                .findByFileTypeAndAttributesMountainCode("path", mountainCode);

        List<MountainFeature> spots = mountainFeatureRepository
                .findByFileTypeAndAttributesMountainCode("spot", mountainCode);

        return MountainDto.builder()
                .info(mountainInfo)
                .paths(paths)
                .spots(spots)
                .build();
    }

    public List<MountainFeature> getMountainPaths(String mountainCode) {
        return mountainFeatureRepository
                .findByFileTypeAndAttributesMountainCode("path", mountainCode);
    }

    public List<MountainFeature> getMountainSpots(String mountainCode) {
        return mountainFeatureRepository
                .findByFileTypeAndAttributesMountainCode("spot", mountainCode);
    }
}
