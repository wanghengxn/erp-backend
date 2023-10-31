package tech.qdhxy.erp.web.rest.accounting.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.qdhxy.erp.common.query.PageQuery;
import tech.qdhxy.erp.domain.accounting.AccountSubject;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
public class AccountSubjectQuery extends PageQuery<AccountSubject> {
    @NotNull
    private String category;
    private Integer status;
    private Boolean showValid;
    @Override
    public Wrapper<AccountSubject> getWrapper() {
        LambdaQueryWrapper<AccountSubject> wrapper = Wrappers.<AccountSubject>lambdaQuery();
        wrapper.eq(AccountSubject::getCategory, category);
        if(Objects.nonNull(status)) {
            wrapper.eq(AccountSubject::getStatus, status);
        }
        if(Objects.nonNull(showValid) && !showValid) {
            wrapper.eq(AccountSubject::getStatus, 1);
        }
        wrapper.orderByAsc(AccountSubject::getCode);
        return wrapper;
    }
}
