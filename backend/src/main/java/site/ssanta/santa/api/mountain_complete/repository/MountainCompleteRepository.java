package site.ssanta.santa.api.mountain_complete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain_complete.domain.MountainComplete;

import java.util.List;

public interface MountainCompleteRepository extends JpaRepository<MountainComplete, Long> {

    List<MountainComplete> findAllByMember(Member member);
    boolean existsByMemberAndMountain(Member member, Mountain mountain);

    MountainComplete findByMemberAndMountain(Member member, Mountain mountain);

    @Query("SELECT mc " +
            "FROM MountainComplete  mc " +
            "LEFT JOIN FETCH mc.mountain m " +
            "LEFT JOIN FETCH mc.member mb " +
            "WHERE m.id = :mountain_id " +
            "AND mb.id = :member_id")
    MountainComplete findByMountainIdAndMemberId(@Param("mountain_id") Long mountainId,
                                                 @Param("member_id") Long memberId);
}
