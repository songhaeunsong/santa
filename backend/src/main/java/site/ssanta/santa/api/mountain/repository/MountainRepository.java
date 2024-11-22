package site.ssanta.santa.api.mountain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.ssanta.santa.api.mountain.domain.Mountain;

import java.util.List;
import java.util.Optional;

public interface MountainRepository extends JpaRepository<Mountain, Long> {

    @Query("SELECT m FROM Mountain m " +
            "LEFT JOIN FETCH m.mountainLikes ml " +
            "WHERE m.province LIKE CONCAT('%', :province, '%') " +
            "AND m.city LIKE CONCAT('%', :city, '%')")
    List<Mountain> findAllByProvinceContainingAndCityContaining(@Param("province") String province,
                                                                @Param("city") String city);

    @Query("SELECT m FROM Mountain m " +
            "LEFT JOIN FETCH m.mountainLikes ml " +
            "WHERE m.id = :id")
    Optional<Mountain> findWithLikesById(@Param("id") Long mountainId);
}
