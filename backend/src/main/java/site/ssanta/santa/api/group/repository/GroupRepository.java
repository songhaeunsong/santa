package site.ssanta.santa.api.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.group.domain.Group;
import site.ssanta.santa.api.group.dto.GroupVO;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<GroupVO> findAllBy();
    Optional<GroupVO> findProjectsById(Long id);
    List<GroupVO> findAllByOrderByExpDescCountOfMembersAscCreateAtDesc();
}
