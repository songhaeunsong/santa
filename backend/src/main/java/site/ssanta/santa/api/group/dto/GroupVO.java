package site.ssanta.santa.api.group.dto;

public interface GroupVO {

    Long getId();
    String getName();
    Long getExp();
    Long getAdminId();
    Long getCountOfMembers();
    String getDescription();
}
