package tech.qdhxy.erp.web.rest.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.domain.accounting.UserAccountSet;
import tech.qdhxy.erp.security.SecurityUtils;
import tech.qdhxy.erp.service.accounting.AccountSetService;
import tech.qdhxy.erp.service.accounting.UserAccountSetService;
import tech.qdhxy.erp.service.accounting.dto.UserAccountSetDTO;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/accounting/user-account-sets")
public class UserAccountSetResource {
    private final UserAccountSetService userAccountSetService;

    @GetMapping("")
    public List<UserAccountSetDTO> getCurrentUserAccountSet() {
        String userCode = SecurityUtils.mustGetCurrentUserLogin();
       return userAccountSetService.getUserAccountSetByUserCode(userCode);
    }

    @PostMapping("")
    public UserAccountSetDTO insert(@Valid @RequestBody UserAccountSetDTO userAccountSetDTO) {
        return userAccountSetService.saveRecord(userAccountSetDTO);
    }
}
