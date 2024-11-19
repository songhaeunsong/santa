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
@RequiredArgsConstructor
public class MountainLikeService {

    private final MountainLikeRepository mountainLikeRepository;
    private final MountainLikeMapper mountainLikeMapper;

    @Transactional
    public void save(Member member, Mountain mountain) {
        if (!mountainLikeMapper.existsByMemberIdAndMountainId(member.getId(), mountain.getId())) {
            mountainLikeRepository.save(MountainLike.builder()
                    .member(member)
                    .mountain(mountain)
                    .build());
        }
    }

    @Transactional
    public void delete(Long userId, Long mountainId) {
        if (mountainLikeMapper.existsByMemberIdAndMountainId(userId, mountainId)) {
            mountainLikeMapper.deleteByUserIdAndMountainId(userId, mountainId);
        }
    }
}
