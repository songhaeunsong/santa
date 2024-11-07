package site.ssanta.santa.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Member save(Member member);
    boolean existsById(Long id);
}
