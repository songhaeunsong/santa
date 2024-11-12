package site.ssanta.santa.api.group_participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.group_participant.repository.GroupParticipantRepository;

@Service
@RequiredArgsConstructor
public class GroupParticipantService {

    private final GroupParticipantRepository groupParticipantRepository;

}
