package tech.qdhxy.erp.service.sso.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SsoUserDTO {
    private String email;
    private String mobile;
    private String name;
    private String realName;
    private Boolean activated;
}
