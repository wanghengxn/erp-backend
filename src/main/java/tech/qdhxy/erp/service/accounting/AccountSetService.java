package tech.qdhxy.erp.service.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import tech.qdhxy.erp.service.mid.DataDictService;
import tech.qdhxy.erp.service.mid.dto.DataDictDTO;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;
import tech.qdhxy.erp.web.rest.accounting.vm.AccountSetPageVM;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountSetService extends ServiceImpl<AccountSetRepository, AccountSet> {
    private final AccountSetMapper accountSetMapper;
    private final UserAccountSetService userAccountSetService;
    private final CodeGetter codeGetter;
    private final DataDictService dataDictService;
    private final AccountSubjectService accountSubjectService;

    public IPage<AccountSetPageVM> getPage(AccountSetQuery query) {
        String userCode = SecurityUtils.mustGetCurrentUserLogin();
        Map<String, String> myAccountSets = userAccountSetService.getUserAccountSetByUserCode(userCode)
                .stream().collect(Collectors.toMap(UserAccountSetDTO::getAccountSetCode, r -> Optional.ofNullable(r.getUserRole()).map(UserRoleEnum::getDesc).orElse("")));
        if (myAccountSets.isEmpty()) {
            return new Page<>();
        }
        query.setCodes(myAccountSets.keySet());
        IPage<AccountSet> ret = this.baseMapper.selectPage(query.getPage(), query.getWrapper());
        Set<String> groupKeys = new HashSet<>();
        groupKeys.add("ACCOUNTING_STANDARDS");
        Map<String, String> dataDictMap = dataDictService.getDictByGroupKeys(groupKeys)
                .stream()
                .collect(Collectors.toMap(r -> r.getGroupKey() + r.getItemKey(), DataDictDTO::getItemValue));


        return ret.convert(e -> {
            AccountSetPageVM dto = accountSetMapper.toPageVm(e);
            dto.setAccountingStandard(dataDictMap.get(String.join("", "ACCOUNTING_STANDARDS", e.getAccountingStandardCode())));
            //dto.setIndustry(dataDictMap.get(String.join("", "INDUSTRY", e.getIndustryCode())));
            //dto.setTaxType(dataDictMap.get(String.join("", "TAX_TYPE", e.getTaxTypeCode())));
            dto.setAccountSetPermission(myAccountSets.get(e.getCode()));
            return dto;
        });
    }

    @Transactional
    public AccountSetDTO saveRecord(AccountSetDTO accountSetDTO) {
        String userCode = SecurityUtils.mustGetCurrentUserLogin();
        if (Objects.isNull(accountSetDTO.getId())) {
            accountSetDTO.setCode(codeGetter.fetch());
            AccountSet accountSet = accountSetMapper.toEntity(accountSetDTO);
            this.baseMapper.insert(accountSet);
            accountSetDTO.setId(accountSet.getId());

            UserAccountSetDTO userAccountSetDTO = new UserAccountSetDTO();
            userAccountSetDTO.setAccountSetCode(accountSet.getCode());
            userAccountSetDTO.setUserCode(userCode);
            userAccountSetDTO.setUserRole(UserRoleEnum.ADMIN);
            userAccountSetService.saveRecord(userAccountSetDTO);
            // 科目初始化
            accountSubjectService.copyTpl(accountSetDTO.getCode());
        } else {
            this.baseMapper.update(null, Wrappers.<AccountSet>lambdaUpdate()
                    .set(AccountSet::getName, accountSetDTO.getName())
                    .set(AccountSet::getStartYear, accountSetDTO.getStartYear())
                    .set(AccountSet::getStartMonth, accountSetDTO.getStartMonth())
                    .set(AccountSet::getUnifiedNumber, accountSetDTO.getUnifiedNumber())
                    .set(AccountSet::getAccountingStandardCode, accountSetDTO.getAccountingStandardCode())
                    .set(AccountSet::getIndustryCode, accountSetDTO.getIndustryCode())
                    .set(AccountSet::getTaxTypeCode, accountSetDTO.getTaxTypeCode())
                    .set(AccountSet::getAssetsModule, accountSetDTO.getAssetsModule())
                    .set(AccountSet::getCashModule, accountSetDTO.getCashModule())
                    .set(AccountSet::getPsiModule, accountSetDTO.getPsiModule())
                    .set(AccountSet::getVoucherAudit, accountSetDTO.getVoucherAudit())
                    .eq(AccountSet::getId, accountSetDTO.getId()));
        }

        return accountSetDTO;
    }

    public AccountSet getOneById(Long id) {
        return this.baseMapper.selectById(id);
    }

    public void deleteById(Long id) {
        this.baseMapper.update(null, Wrappers.<AccountSet>lambdaUpdate().set(AccountSet::getStatus, 0).eq(AccountSet::getId, id));
    }
}
