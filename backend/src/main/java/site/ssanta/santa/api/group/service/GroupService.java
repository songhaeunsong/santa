package site.ssanta.santa.api.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.group.dto.GroupVO;
import site.ssanta.santa.api.group.repository.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<GroupVO> getAllGroups() {
        return groupRepository.findAllProjects();
    }

    public GroupVO findById(Long groupId) {
        return groupRepository.findProjectsById(groupId);
    }
}
