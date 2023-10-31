package tech.qdhxy.erp.service.accounting.mapper;

import org.mapstruct.Mapper;
import tech.qdhxy.erp.common.EntityMapper;
import tech.qdhxy.erp.domain.accounting.AccountSubject;
import tech.qdhxy.erp.domain.accounting.AccountSubjectTpl;
import tech.qdhxy.erp.service.accounting.dto.AccountSubjectDTO;

@Mapper(componentModel = "spring", uses = {})
public interface AccountSubjectMapper extends EntityMapper<AccountSubjectDTO, AccountSubject> {

    AccountSubject fromTpl(AccountSubjectTpl tpl);
}
