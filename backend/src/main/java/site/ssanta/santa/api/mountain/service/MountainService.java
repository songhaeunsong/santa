package site.ssanta.santa.api.mountain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.domain.MountainPath;
import site.ssanta.santa.api.mountain.domain.MountainInfo;
import site.ssanta.santa.api.mountain.domain.MountainSpot;
import site.ssanta.santa.api.mountain.dto.MountainDto;
import site.ssanta.santa.api.mountain.repository.MountainFeatureRepository;
import site.ssanta.santa.api.mountain.repository.MountainInfoRepository;
import site.ssanta.santa.api.mountain.repository.MountainRepository;

import java.util.List;

@Slf4j
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

        List<MountainPath> paths = mountainFeatureRepository
                .findMountainPathsByAttributes_MountainCode(mountainCode);

        List<MountainSpot> spots = mountainFeatureRepository
                .findMountainSpotsByAttributes_MountainCode(mountainCode);

        return MountainDto.builder()
                .info(mountainInfo)
                .paths(paths)
                .spots(spots)
                .build();
    }

    public List<MountainPath> getMountainPaths(String mountainCode) {
        return mountainFeatureRepository
                .findMountainPathsByAttributes_MountainCode(mountainCode);
    }

    public List<MountainSpot> getMountainSpots(String mountainCode) {
        return mountainFeatureRepository
                .findMountainSpotsByAttributes_MountainCode(mountainCode);
    }
}
