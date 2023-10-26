package tech.qdhxy.erp.service.accounting.mapper;

import org.mapstruct.Mapper;
import tech.qdhxy.erp.common.EntityMapper;
import tech.qdhxy.erp.domain.accounting.UserAccountSet;
import tech.qdhxy.erp.service.accounting.dto.UserAccountSetDTO;

@Mapper(componentModel = "spring", uses = {})
public interface UserAccountSetMapper extends EntityMapper<UserAccountSetDTO, UserAccountSet> {
}
