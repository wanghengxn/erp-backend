package tech.qdhxy.erp.web.rest.accounting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.service.accounting.AccountSetService;
import tech.qdhxy.erp.service.accounting.AccountSubjectService;
import tech.qdhxy.erp.service.accounting.dto.AccountSetDTO;
import tech.qdhxy.erp.service.accounting.dto.AccountSubjectDTO;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSetQuery;
import tech.qdhxy.erp.web.rest.accounting.query.AccountSubjectQuery;
import tech.qdhxy.erp.web.rest.accounting.vm.AccountSetPageVM;
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

//    @PostMapping("")
//    public AccountSetDTO insert(@Valid @RequestBody AccountSetDTO accountSetDTO) {
//        return accountSubjectService.saveRecord(accountSetDTO);
//    }
//
//    @PutMapping("")
//    public AccountSetDTO update(@Valid @RequestBody AccountSetDTO accountSetDTO) {
//        return accountSubjectService.saveRecord(accountSetDTO);
//    }
//
//    @GetMapping("/{id}")
//    public AccountSet getOne(@NotNull @PathVariable Long id) {
//        return accountSubjectService.getOneById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@NotNull @PathVariable Long id) {
//        this.accountSubjectService.deleteById(id);
//    }

        @PutMapping("/batch-operation")
    public void doBatchOperation(@Valid @RequestBody SubjectBatchOperationVM operationVM) {
        if(SubjectBatchOperationVM.ENABLE_ALL.equals(operationVM.getAction())) {
            accountSubjectService.updateStateByIds(operationVM.getIds(), true);
        }  else if(SubjectBatchOperationVM.DISABLE_ALL.equals(operationVM.getAction())) {
            accountSubjectService.updateStateByIds(operationVM.getIds(), false);
        }
    }
}
