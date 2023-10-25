package tech.qdhxy.erp.web.rest.sso.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.qdhxy.erp.common.query.PageQuery;
import tech.qdhxy.erp.domain.sso.SsoUser;

public class SsoUserQuery extends PageQuery<SsoUser> {
    @Override
    public Wrapper<SsoUser> getWrapper() {
        return Wrappers.emptyWrapper();
    }
}
