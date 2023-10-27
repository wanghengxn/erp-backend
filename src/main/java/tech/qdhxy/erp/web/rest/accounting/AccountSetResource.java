package tech.qdhxy.erp.web.rest.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.service.accounting.AccountSetService;
import tech.qdhxy.erp.service.accounting.dto.AccountSetDTO;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/accounting/account-sets")
public class AccountSetResource {
    private final AccountSetService accountSetService;

    @GetMapping("")
    public MyPage<AccountSet> getPage(AccountSetQuery query) {
        IPage<AccountSet> page = accountSetService.getPage(query);
        return MyPage.of(page);
    }

    @PostMapping("")
    public AccountSetDTO insert(@Valid @RequestBody AccountSetDTO accountSetDTO) {
        return accountSetService.saveRecord(accountSetDTO);
    }

    @GetMapping("/{id}")
    public AccountSet getOne(@NotNull @PathVariable Long id) {
        return accountSetService.getOneById(id);
    }
}
