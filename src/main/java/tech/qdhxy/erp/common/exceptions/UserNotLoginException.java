package tech.qdhxy.erp.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotLoginException extends RuntimeException {
    public UserNotLoginException() {
        super("用户未登录");
    }
}
