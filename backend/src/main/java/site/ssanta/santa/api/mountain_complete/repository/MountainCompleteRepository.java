package site.ssanta.santa.api.mountain_complete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.mountain_complete.domain.MountainComplete;

import java.util.List;

public interface MountainCompleteRepository extends JpaRepository<MountainComplete, Long> {

    List<MountainComplete> findAllByMember(Member member);
}
