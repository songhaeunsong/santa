package site.ssanta.santa.api.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    @Setter
    private Boolean isNew;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
