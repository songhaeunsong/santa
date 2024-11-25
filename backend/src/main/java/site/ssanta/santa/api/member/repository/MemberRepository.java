package site.ssanta.santa.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.member.dto.MemberInfoVO;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Member save(Member member);
    boolean existsById(Long id);
    MemberInfoVO findProjectsById(Long id);
    boolean existsMemberByNicknameEqualsIgnoreCase(String nickname);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.mountainLikes ml " +
            "LEFT JOIN FETCH ml.mountain " +
            "WHERE m.id = :id")
    Member findWithMountainLikesById(@Param("id") Long userId);

    List<MemberInfoVO> findAllByOrderByTierDescExpDesc();

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.mountainCompletes mc " +
            "LEFT JOIN FETCH mc.mountain " +
            "WHERE m.id = :id")
    Member findWithMountainCompletesById(@Param("id") Long userId);
}
