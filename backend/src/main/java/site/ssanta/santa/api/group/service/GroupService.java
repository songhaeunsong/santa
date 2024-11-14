package site.ssanta.santa.api.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.group.domain.Group;
import site.ssanta.santa.api.group.dto.CreateGroupRequestDto;
import site.ssanta.santa.api.group.dto.GroupVO;
import site.ssanta.santa.api.group.exception.GroupNotFoundException;
import site.ssanta.santa.api.group.repository.GroupRepository;
import site.ssanta.santa.api.member.dto.MemberInfoVO;

import java.util.Date;
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

    @Transactional
    public Long createGroup(MemberInfoVO member, CreateGroupRequestDto dto) {
        Group group = groupRepository.save(Group.builder()
                .adminId(member.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .exp(member.getExp())
                .createAt(new Date())
                .countOfMembers(1L)
                .build());

        return group.getId();
    }

    @Transactional
    public void join(MemberInfoVO member, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        group.joinMember(member);
    }
}
