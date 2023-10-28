package tech.qdhxy.erp.web.rest.accounting.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.common.query.PageQuery;
import tech.qdhxy.erp.domain.accounting.AccountSet;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class AccountSetQuery extends PageQuery<AccountSet> {
    private Set<String> codes;
    private String name;
    @Override
    public Wrapper<AccountSet> getWrapper() {
        LambdaQueryWrapper<AccountSet> wrapper = Wrappers.<AccountSet>lambdaQuery();
        if(Objects.nonNull(codes) && !codes.isEmpty()) {
            wrapper.in(AccountSet::getCode, codes);
        }
        if(StringUtils.isNotBlank(name)) {
            wrapper.like(AccountSet::getName, "%" + name + "%");
        }
        return wrapper;
    }
}
