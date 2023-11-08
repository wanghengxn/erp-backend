package tech.qdhxy.erp.service.sso.mapper;

import org.mapstruct.Mapper;
import tech.qdhxy.erp.domain.sso.SsoUser;
import tech.qdhxy.erp.common.EntityMapper;
import tech.qdhxy.erp.service.sso.dto.SsoUserDTO;
import tech.qdhxy.erp.service.sso.dto.SsoUserDetailDTO;

@Mapper(componentModel = "spring", uses = {})
public interface SsoUserMapper extends EntityMapper<SsoUserDTO, SsoUser> {

    SsoUserDetailDTO toDetailDto(SsoUser entity);
}
