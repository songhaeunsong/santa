package site.ssanta.santa.api.group_participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.group_participant.domain.GroupParticipant;
import site.ssanta.santa.api.group_participant.dto.ParticipantVO;

import java.util.List;

public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {

    List<ParticipantVO> findAllByGroupId(Long groupId);

    List<GroupParticipant> findAllByMemberId(Long memberId);
}
