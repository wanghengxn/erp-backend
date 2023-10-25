package tech.qdhxy.erp.domain.sso;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.domain.BaseEntity;

@Getter
@Setter
@TableName("erp_sso_user")
public class SsoUser extends BaseEntity {
    private String email;
    private String mobile;
    private String pwd;
    private String name;
    private String realName;
    private Boolean activated;
}
