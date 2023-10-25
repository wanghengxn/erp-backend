package tech.qdhxy.erp.service.sso.mapper;

import org.mapstruct.Mapper;
import tech.qdhxy.erp.domain.sso.SsoUser;
import tech.qdhxy.erp.common.EntityMapper;
import tech.qdhxy.erp.service.sso.dto.SsoUserDTO;

@Mapper(componentModel = "spring", uses = {})
public interface SsoUserMapper extends EntityMapper<SsoUserDTO, SsoUser> {
}
