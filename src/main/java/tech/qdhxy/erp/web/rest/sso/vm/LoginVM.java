package tech.qdhxy.erp.web.rest.sso.vm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class LoginVM {
    @NotNull(message = "不能为空")
    @Size(min = 1, max = 50, message = "用户名长度在1-50字符之间")
    private String username;

    @NotNull
    @Size(min = 4, max = 100, message = "密码长度在4-100个字符之间")
    private String password;

    private Boolean rememberMe;

    @NotNull(message = "登录方式不能为空")
    private LoginType loginType = LoginType.MOBILE;


}
