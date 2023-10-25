package tech.qdhxy.erp.web.rest.accounting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.domain.mid.DataDict;
import tech.qdhxy.erp.service.accounting.AccountSetService;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;

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
}
