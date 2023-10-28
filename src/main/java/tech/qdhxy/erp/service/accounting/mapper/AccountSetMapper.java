package tech.qdhxy.erp.service.accounting.mapper;

import org.mapstruct.Mapper;
import tech.qdhxy.erp.common.EntityMapper;
import tech.qdhxy.erp.domain.accounting.AccountSet;
import tech.qdhxy.erp.service.accounting.dto.AccountSetDTO;
import tech.qdhxy.erp.web.rest.accounting.vm.AccountSetPageVM;

@Mapper(componentModel = "spring", uses = {})
public interface AccountSetMapper extends EntityMapper<AccountSetDTO, AccountSet> {

    AccountSetPageVM toPageVm(AccountSet accountSet);
}
