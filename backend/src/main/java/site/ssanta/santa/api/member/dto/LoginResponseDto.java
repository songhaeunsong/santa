package site.ssanta.santa.api.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonIgnoreProperties({"accessToken", "refreshToken"})
public class LoginResponseDto {

    @JsonIgnore
    private String accessToken;

    @JsonIgnore
    private String refreshToken;

    @Setter
    private Boolean isNew;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
