package tech.qdhxy.erp.service.accounting.dto;

import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.domain.accounting.enums.UserRoleEnum;

@Getter
@Setter
public class UserAccountSetDTO {
    private Long id;
    // 用户代码
    private String userCode;
    // 帐套代码
    private String accountSetCode;
    // 是否选择
    private Boolean selected;
    // 用户在帐套的角色
    private UserRoleEnum userRole;

    private String accountSetName;
    private Integer accountSetStartYear;
    private Integer accountSetStartMonth;
}
