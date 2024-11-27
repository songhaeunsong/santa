package site.ssanta.santa.api.mountain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.domain.MountainPath;
import site.ssanta.santa.api.mountain.domain.MountainInfo;
import site.ssanta.santa.api.mountain.domain.MountainSpot;
import site.ssanta.santa.api.mountain.dto.MountainDetailDto;
import site.ssanta.santa.api.mountain.dto.MountainQueryResponseDto;
import site.ssanta.santa.api.mountain.repository.MountainFeatureRepository;
import site.ssanta.santa.api.mountain.repository.MountainInfoRepository;
import site.ssanta.santa.api.mountain.repository.MountainRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public MountainDetailDto getMountainInfo(String mountainCode, Long userId) {
        CompletableFuture<MountainInfo> info = CompletableFuture.supplyAsync(() ->
                mountainInfoRepository.findByMountainCode(mountainCode)
                        .orElseThrow());

        CompletableFuture<List<MountainPath>> paths = CompletableFuture.supplyAsync(() ->
                mountainFeatureRepository.findMountainPathsByAttributes_MountainCode(mountainCode));

        CompletableFuture<MountainQueryResponseDto> mountainInfo = CompletableFuture.supplyAsync(() ->
                mountainRepository.findWithLikesById(Long.parseLong(mountainCode))
                .stream()
                .map(mountain ->
                    MountainQueryResponseDto.builder()
                            .id(mountain.getId())
                            .image(mountain.getImage())
                            .name(mountain.getName())
                            .city(mountain.getCity())
                            .province(mountain.getProvince())
                            .height(mountain.getHeight())
                            .longitude(new BigDecimal(mountain.getLongitude()))
                            .latitude(new BigDecimal(mountain.getLatitude()))
                            .isLiked(mountain.getMountainLikes().stream()
                                    .anyMatch(mountainLike -> mountainLike.getMember().getId().equals(userId)))
                            .build()
                )
                .findFirst()
                .orElseThrow());

        return MountainDetailDto.builder()
                .info(info.join())
                .paths(paths.join())
                .mountainInfo(mountainInfo.join())
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
