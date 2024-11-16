package site.ssanta.santa.api.mountain_like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain_like.domain.MountainLike;
import site.ssanta.santa.api.mountain_like.repository.MountainLikeRepository;

@Service
@RequiredArgsConstructor
public class MountainLikeService {

    private final MountainLikeRepository mountainLikeRepository;

    @Transactional
    public void save(Member member, Mountain mountain) {
        mountainLikeRepository.save(MountainLike.builder()
                .member(member)
                .mountain(mountain)
                .build());
    }
}
