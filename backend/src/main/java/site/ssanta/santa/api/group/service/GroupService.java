package site.ssanta.santa.api.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.group.dto.GroupVO;
import site.ssanta.santa.api.group.exception.GroupNotFoundException;
import site.ssanta.santa.api.group.repository.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public List<GroupVO> getAllGroups() {
        return groupRepository.findAllBy();
    }

    @Transactional(readOnly = true)
    public GroupVO findById(Long groupId) {
        return groupRepository.findProjectsById(groupId)
                .orElseThrow(GroupNotFoundException::new);
    }
}
