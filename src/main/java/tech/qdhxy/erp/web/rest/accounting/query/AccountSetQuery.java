package tech.qdhxy.erp.web.rest.accounting.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.qdhxy.erp.common.query.PageQuery;
import tech.qdhxy.erp.domain.accounting.AccountSet;

public class AccountSetQuery extends PageQuery<AccountSet> {
    @Override
    public Wrapper<AccountSet> getWrapper() {
        return Wrappers.emptyWrapper();
    }
}
