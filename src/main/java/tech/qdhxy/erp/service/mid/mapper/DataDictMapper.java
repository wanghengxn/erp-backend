package tech.qdhxy.erp.service.mid.mapper;

import org.mapstruct.Mapper;
import tech.qdhxy.erp.common.EntityMapper;
import tech.qdhxy.erp.domain.mid.DataDict;
import tech.qdhxy.erp.service.mid.dto.DataDictDTO;

@Mapper(componentModel = "spring", uses = {})
public interface DataDictMapper extends EntityMapper<DataDictDTO, DataDict> {
}
