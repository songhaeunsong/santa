package site.ssanta.santa.api.mountain_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.mountain_like.domain.MountainLike;

public interface MountainLikeRepository extends JpaRepository<MountainLike, Long> {
}
