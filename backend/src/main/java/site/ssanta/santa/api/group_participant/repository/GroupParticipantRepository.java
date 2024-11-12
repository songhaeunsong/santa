package site.ssanta.santa.api.group_participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.group_participant.domain.GroupParticipant;

public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {
}
