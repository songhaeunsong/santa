package site.ssanta.santa.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.member.domain.Tier;
import site.ssanta.santa.api.member.dto.*;
import site.ssanta.santa.api.member.repository.MemberRepository;
import site.ssanta.santa.common.jwt.JwtUtil;
import site.ssanta.santa.common.jwt.exception.JWTErrorCode;
import site.ssanta.santa.common.jwt.exception.MemberNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
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
                .email(userInfo.getKakaoAccount().getEmail())
                .tier(Tier.BRONZE)
                .createAt(new Date())
                .exp(0L)
                .uuid(UUID.randomUUID().toString())
                .profileUrl(userInfo.getKakaoAccount().getProfile().getThumbnailImage())
                .build();

        memberRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    protected boolean isUser(Long id) {
        return memberRepository.existsById(id);
    }

    public String reissueToken(String token) {
        return jwtUtil.reissue(token);
    }

    @Transactional
    public void setNickname(Long userId, SetNicknameDto dto) {
        Member find = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException(JWTErrorCode.ERR_NOT_FOUND_MEMBER.toString()));

        find.updateNickname(dto.getNickname());
    }

    @Transactional(readOnly = true)
    public MemberInfoVO getUserInfo(Long userId) {
        MemberInfoVO result = memberRepository.findProjectsById(userId);
        if (result == null) {
            throw new MemberNotFoundException(JWTErrorCode.ERR_NOT_FOUND_MEMBER.toString());
        }

        return result;
    }

    @Transactional(readOnly = true)
    public CheckNicknameResponseDto checkNickname(String nickname) {
        boolean result = memberRepository.existsMemberByNicknameEqualsIgnoreCase(nickname);
        return new CheckNicknameResponseDto(result);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow();
    }

    public Member getMountainLikes(Long userId) {
        return memberRepository.findWithMountainLikesById(userId);
    }

    @Transactional
    public void updateExp(Member member, int exp) {
        member.updateExp(exp);
    }

    @Transactional(readOnly = true)
    public List<MemberInfoVO> getMemberRank() {
        return memberRepository.findAllByOrderByTierDescExpDesc();
    }
}
