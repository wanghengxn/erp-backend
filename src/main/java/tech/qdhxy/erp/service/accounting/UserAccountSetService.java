package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.domain.accounting.UserAccountSet;
import tech.qdhxy.erp.repository.accounting.AccountSetRepository;
import tech.qdhxy.erp.repository.accounting.UserAccountSetRepository;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAccountSetService extends ServiceImpl<UserAccountSetRepository, UserAccountSet> {
    public String getSelectedAccountSetCodeByUserCode(String userCode) {
        return Optional.ofNullable(this.baseMapper.selectOne(Wrappers.<UserAccountSet>lambdaQuery()
                .eq(UserAccountSet::getUserCode, userCode)
                .eq(UserAccountSet::getSelected, true)))
                .map(UserAccountSet::getAccountSetCode)
                .orElse("");
    }
}
