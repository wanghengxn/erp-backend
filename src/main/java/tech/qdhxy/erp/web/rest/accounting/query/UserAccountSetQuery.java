package tech.qdhxy.erp.web.rest.accounting.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.qdhxy.erp.common.query.PageQuery;
import tech.qdhxy.erp.domain.accounting.UserAccountSet;

public class UserAccountSetQuery extends PageQuery<UserAccountSet> {
    @Override
    public Wrapper<UserAccountSet> getWrapper() {
        return Wrappers.emptyWrapper();
    }
}
