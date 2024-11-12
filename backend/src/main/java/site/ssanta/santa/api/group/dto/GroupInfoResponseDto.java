package site.ssanta.santa.api.group.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInfoResponseDto {

    private Long id;
    private String name;
    private Long exp;
    private Long adminId;
    private String description;
    private Long countOfMembers;
}
