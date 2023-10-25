package tech.qdhxy.erp.web.rest.sso.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateCurrentUserPwdVM {
    @NotNull
    private String oldPwd;
    @NotNull
    private String newPwd;
    @NotNull
    private String confirmNewPwd;
}
