package tech.qdhxy.erp.service.sso.dto;

import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.service.accounting.dto.UserAccountSetDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SsoUserDTO {
    private String code;
    private String email;
    private String mobile;
    private String name;
    private String realName;
    private Boolean activated;
    // 用户帐套
    private List<UserAccountSetDTO> accountSets = new ArrayList<>();
    // 用户选择的帐套
    private UserAccountSetDTO selectedAccountSet;

    public UserAccountSetDTO getSelectedAccountSet() {
        return this.accountSets.stream().filter(UserAccountSetDTO::getSelected).findFirst().orElse(null);
    }
}
