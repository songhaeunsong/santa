package site.ssanta.santa.api.group_participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.group_participant.domain.GroupParticipant;
import site.ssanta.santa.api.group_participant.domain.Role;
import site.ssanta.santa.api.group_participant.dto.ParticipantVO;
import site.ssanta.santa.api.group_participant.repository.GroupParticipantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupParticipantService {

    private final GroupParticipantRepository groupParticipantRepository;

    @Transactional(readOnly = true)
    public List<ParticipantVO> findByGroupId(Long groupId) {
        return groupParticipantRepository.findAllByGroupId(groupId);
    }

    @Transactional
    public void save(Long userId, Long groupId, Role role) {
        groupParticipantRepository.save(GroupParticipant.builder()
                .id(userId)
                .role(role)
                .groupId(groupId)
                .memberId(userId)
                .build());
    }

    public List<GroupParticipant> findAllByMemberId(Long memberId) {
        return groupParticipantRepository.findAllByMemberId(memberId);
    }
}
