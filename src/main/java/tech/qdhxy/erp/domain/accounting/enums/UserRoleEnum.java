package tech.qdhxy.erp.domain.accounting.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum UserRoleEnum {
    ADMIN("帐套管理员"),
    USER("帐套协作者"),
    ;
    private final String desc;
    UserRoleEnum(String desc) {
        this.desc = desc;
    }
}
