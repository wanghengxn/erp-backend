package tech.qdhxy.erp.web.rest.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.qdhxy.erp.common.exceptions.BadRequestException;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.security.SecurityUtils;
import tech.qdhxy.erp.service.accounting.AccountSubjectService;
import tech.qdhxy.erp.service.accounting.dto.AccountSubjectDTO;
import tech.qdhxy.erp.service.sso.dto.SsoUserDetailDTO;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSubjectQuery;
import tech.qdhxy.erp.web.rest.accounting.vm.SubjectBatchOperationVM;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/accounting/account-subjects")
public class AccountSubjectResource {
    private final AccountSubjectService accountSubjectService;

    @GetMapping("")
    public MyPage<AccountSubjectDTO> getPage(AccountSubjectQuery query) {
        IPage<AccountSubjectDTO> page = accountSubjectService.getPage(query);
        return MyPage.of(page);
    }

    @PostMapping("")
    public AccountSubjectDTO insert(@Valid @RequestBody AccountSubjectDTO accountSubjectDTO) {
        SsoUserDetailDTO user = SecurityUtils.mustGetLoginUser();
        accountSubjectDTO.setAccountSetCode(user.getSelectedAccountSetCode());
        // 参数校验，只允许四级
        if(accountSubjectDTO.getLevel() > 4) {
            throw new BadRequestException("只允许存在4级科目");
        }
        // 检查帐套是否允许新增一级科目 TODO

        // 上级是否存在

        return accountSubjectService.saveRecord(accountSubjectDTO);
    }

    @PutMapping("")
    public AccountSubjectDTO update(@Valid @RequestBody AccountSubjectDTO accountSubjectDTO) {
        return accountSubjectService.saveRecord(accountSubjectDTO);
    }

    @GetMapping("/{id}")
    public AccountSubjectDTO getOne(@NotNull @PathVariable Long id) {
        return accountSubjectService.getOneById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@NotNull @PathVariable Long id) {
        this.accountSubjectService.deleteById(id);
    }

    @PutMapping("/batch-operation")
    public void doBatchOperation(@Valid @RequestBody SubjectBatchOperationVM operationVM) {
        if(SubjectBatchOperationVM.ENABLE_ALL.equals(operationVM.getAction())) {
            accountSubjectService.updateStateByIds(operationVM.getIds(), true);
        }  else if(SubjectBatchOperationVM.DISABLE_ALL.equals(operationVM.getAction())) {
            accountSubjectService.updateStateByIds(operationVM.getIds(), false);
        }
    }
}
