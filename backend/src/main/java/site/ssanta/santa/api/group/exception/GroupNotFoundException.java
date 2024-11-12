package site.ssanta.santa.api.group.exception;

import org.springframework.http.HttpStatus;
import site.ssanta.santa.common.exception.CustomException;

public class GroupNotFoundException extends CustomException {

    public GroupNotFoundException() {
        super(HttpStatus.NOT_FOUND, GroupErrorCode.ERR_NOT_FOUND_GROUP.name());
    }
}
