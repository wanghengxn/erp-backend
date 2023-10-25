package tech.qdhxy.erp.domain.accounting;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.domain.BaseEntity;
import tech.qdhxy.erp.domain.accounting.enums.UserRoleEnum;

@Getter
@Setter
@TableName("erp_ac_user_account_set")
public class UserAccountSet extends BaseEntity {
    // 用户代码
    private String userCode;
    // 帐套代码
    private String accountSetCode;
    // 是否选择
    private String selected;
    // 用户在帐套的角色
    private UserRoleEnum userRole;
}
