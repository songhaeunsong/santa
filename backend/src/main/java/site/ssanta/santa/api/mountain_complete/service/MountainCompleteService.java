package site.ssanta.santa.api.mountain_complete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain_complete.dto.MountainCompleteVO;
import site.ssanta.santa.api.mountain_complete.repository.MountainCompleteRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MountainCompleteService {

    private final MountainCompleteRepository mountainCompleteRepository;

    public List<MountainCompleteVO> getCompleteByMember(Member member) {
        return mountainCompleteRepository.findAllByMember(member).stream()
                .map(mountainComplete -> MountainCompleteVO.builder()
                        .id(mountainComplete.getId())
                        .mountainId(mountainComplete.getMountain().getId())
                        .mountainName(mountainComplete.getMountain().getName())
                        .mountainImage(mountainComplete.getMountain().getImage())
                        .build())
                .toList();
    }
}
