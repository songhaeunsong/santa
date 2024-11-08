package site.ssanta.santa.api.member.dto;

import lombok.Getter;

@Getter
public class CheckNicknameResponseDto {

    private Boolean isDuplicated;

    public CheckNicknameResponseDto(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
