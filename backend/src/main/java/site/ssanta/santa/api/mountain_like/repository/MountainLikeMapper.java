package site.ssanta.santa.api.mountain_like.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MountainLikeMapper {

    boolean existsByMemberIdAndMountainId(
            @Param("memberId") Long memberId,
            @Param("mountainId") Long mountainId
    );

    void deleteByMemberIdAndMountainId(
            @Param("memberId") Long memberId,
            @Param("mountainId") Long mountainId
    );
}
