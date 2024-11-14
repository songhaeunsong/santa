package site.ssanta.santa.api.mountain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.mountain.domain.Mountain;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
}
