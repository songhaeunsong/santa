package site.ssanta.santa.api.mountain_complete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain_complete.domain.MountainComplete;
import site.ssanta.santa.api.mountain_complete.dto.MountainCompleteVO;
import site.ssanta.santa.api.mountain_complete.repository.MountainCompleteRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MountainCompleteService {

    private final MountainCompleteRepository mountainCompleteRepository;

    @Transactional(readOnly = true)
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

    public boolean save(Member member, Mountain mountain) {
        if (!mountainCompleteRepository.existsByMemberAndMountain(member, mountain)) {
            mountainCompleteRepository.save(MountainComplete.builder()
                    .member(member)
                    .mountain(mountain)
                    .build());
            return true;
        }

        MountainComplete mountainComplete = mountainCompleteRepository.findByMemberAndMountain(member, mountain);

        return !isSameDay(mountainComplete.getCreateAt());
    }

    private static boolean isSameDay(Date date) {
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(now);
        cal2.setTime(date);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
