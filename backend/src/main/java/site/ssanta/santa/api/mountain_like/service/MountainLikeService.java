package site.ssanta.santa.api.mountain_like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain_like.domain.MountainLike;
import site.ssanta.santa.api.mountain_like.repository.MountainLikeMapper;
import site.ssanta.santa.api.mountain_like.repository.MountainLikeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MountainLikeService {

    private final MountainLikeRepository mountainLikeRepository;
    private final MountainLikeMapper mountainLikeMapper;

    public void save(Member member, Mountain mountain) {
        if (!mountainLikeMapper.existsByMemberIdAndMountainId(member.getId(), mountain.getId())) {
            mountainLikeRepository.save(MountainLike.builder()
                    .member(member)
                    .mountain(mountain)
                    .build());
        }
    }

    public void delete(Long memberId, Long mountainId) {
        if (mountainLikeMapper.existsByMemberIdAndMountainId(memberId, mountainId)) {
            mountainLikeMapper.deleteByMemberIdAndMountainId(memberId, mountainId);
        }
    }
}
