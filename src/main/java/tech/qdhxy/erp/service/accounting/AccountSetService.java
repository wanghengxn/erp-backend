package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.qdhxy.erp.common.CodeGetter;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.domain.accounting.enums.UserRoleEnum;
import tech.qdhxy.erp.repository.accounting.AccountSetRepository;
import tech.qdhxy.erp.security.SecurityUtils;
import tech.qdhxy.erp.service.accounting.dto.AccountSetDTO;
import tech.qdhxy.erp.service.accounting.dto.UserAccountSetDTO;
import tech.qdhxy.erp.service.accounting.mapper.AccountSetMapper;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AccountSetService extends ServiceImpl<AccountSetRepository, AccountSet> {
    private final AccountSetMapper accountSetMapper;
    private final UserAccountSetService userAccountSetService;
    private final CodeGetter codeGetter;
    public IPage<AccountSet> getPage(AccountSetQuery query) {
        return this.baseMapper.selectPage(query.getPage(), query.getWrapper());
    }

    @Transactional
    public AccountSetDTO saveRecord(AccountSetDTO accountSetDTO) {
        String userCode = SecurityUtils.mustGetCurrentUserLogin();
        AccountSet accountSet = null;
        if(Objects.isNull(accountSetDTO.getId())) {
            accountSetDTO.setCode(codeGetter.fetch());
            accountSet = accountSetMapper.toEntity(accountSetDTO);
            this.baseMapper.insert(accountSet);
            accountSetDTO.setId(accountSet.getId());
        } else {
            accountSet = this.baseMapper.selectOne(Wrappers.<AccountSet>lambdaQuery()
                    .eq(AccountSet::getId, accountSetDTO.getId()));
        }
        if(Objects.nonNull(accountSet)) {
            UserAccountSetDTO userAccountSetDTO = new UserAccountSetDTO();
            userAccountSetDTO.setAccountSetCode(accountSet.getCode());
            userAccountSetDTO.setUserCode(userCode);
            userAccountSetDTO.setUserRole(UserRoleEnum.ADMIN);
            userAccountSetService.saveRecord(userAccountSetDTO);
        }
        return accountSetDTO;
    }
}
