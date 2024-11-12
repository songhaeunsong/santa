package site.ssanta.santa.api.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.group.domain.GroupParticipant;

public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {
}
