package site.ssanta.santa.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.member.domain.Tier;
import site.ssanta.santa.api.member.dto.*;
import site.ssanta.santa.api.member.repository.MemberRepository;
import site.ssanta.santa.common.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public LoginResponseDto getToken(KakaoUserInfoResponseDto userInfo) {
        boolean isNew = !isUser(userInfo.getId());
        if (isNew) {
            save(userInfo);
        }

        LoginResponseDto result = jwtUtil.getTokens(userInfo.getId());
        result.setIsNew(isNew);
        return result;
    }

    private void save(KakaoUserInfoResponseDto userInfo) {
        Member newUser = Member.builder()
                .id(userInfo.getId())
                .nickname(userInfo.getProperties().getNickname())
                .email(userInfo.getEmail())
                .tier(Tier.BRONZE)
                .profileUrl(userInfo.getKakaoAccount().getProfile().getThumbnailImage())
                .build();

        memberRepository.save(newUser);
    }

    private boolean isUser(Long id) {
        return memberRepository.existsById(id);
    }

    public VerifyTokenResponseDto getNickname(String token) {
        String[] data = token.split(" ");
        token = data[1];

        Long userId = jwtUtil.getUserId(token);
        Member user = memberRepository.findById(userId)
                .orElseThrow();

        return new VerifyTokenResponseDto(user.getNickname());
    }

    public ReIssueTokenDto reissueToken(String token) {
        String accessToken = jwtUtil.reissue(token);
        return new ReIssueTokenDto(accessToken);
    }
}
