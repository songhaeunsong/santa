package site.ssanta.santa.api.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.group.domain.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findById(Long id);
}
