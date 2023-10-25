package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.repository.accounting.AccountSetRepository;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;

@Service
@AllArgsConstructor
public class AccountSetService extends ServiceImpl<AccountSetRepository, AccountSet> {
    public IPage<AccountSet> getPage(AccountSetQuery query) {
        return this.baseMapper.selectPage(query.getPage(), query.getWrapper());
    }
}
