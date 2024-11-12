package site.ssanta.santa.api.group_participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.group_participant.dto.ParticipantVO;
import site.ssanta.santa.api.group_participant.repository.GroupParticipantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupParticipantService {

    private final GroupParticipantRepository groupParticipantRepository;


    public List<ParticipantVO> findByGroupId(Long groupId) {
        return groupParticipantRepository.findAllByGroupId(groupId);
    }
}
